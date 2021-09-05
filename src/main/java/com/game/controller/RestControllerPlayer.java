package com.game.controller;

import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import com.game.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/rest/players")
public class RestControllerPlayer {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable("id") Long playerId) {
        if (playerId == null || playerId == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!playerService.existsById(playerId))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Player player = playerService.getById(playerId);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        HttpHeaders headers = new HttpHeaders();

        try {
            playerService.save(player);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(player, headers, HttpStatus.OK);
    }

    @GetMapping("")
    @ResponseBody
    public ResponseEntity<List<Player>> getPageBooks(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "3") Integer pageSize,
            @RequestParam(required = false, defaultValue = "ID") PlayerOrder order,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam(required = false, defaultValue = "") Race race,
            @RequestParam(required = false, defaultValue = "") Profession profession,
            @RequestParam(required = false, defaultValue = "") Boolean banned,
            @RequestParam(required = false, defaultValue = "") Long after,
            @RequestParam(required = false, defaultValue = "") Long before,
            @RequestParam(required = false, defaultValue = "") Integer minExperience,
            @RequestParam(required = false, defaultValue = "") Integer maxExperience,
            @RequestParam(required = false, defaultValue = "") Integer minLevel,
            @RequestParam(required = false, defaultValue = "") Integer maxLevel
            ) {
        Sort sort = null;
        switch (order) {
            case ID:
                sort = Sort.by("id");
                break;
            case NAME:
                sort = Sort.by("name");
                break;
            case EXPERIENCE:
                sort = Sort.by("experience");
                break;
            case BIRTHDAY:
                sort = Sort.by("birthday");
                break;
        }

        PageRequest pageRequest = PageRequest.of(pageNumber,pageSize,sort);
        List<Player> players = playerService.searchByCriterias(name,title,race,profession, banned, after, before,
                                                  minExperience, maxExperience, minLevel, maxLevel, pageRequest).toList();
        return new ResponseEntity<>(players,HttpStatus.OK);
    }

    @PostMapping(value = "/{id}")
    public @ResponseBody
    ResponseEntity<Player> update(@RequestBody Player player, @PathVariable Long id) {

        Player up = playerService.update(player, id);

        if(up == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if(up.getId() == null || up.getId() <= 0 || !(up.getId() instanceof Long))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(up, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Player> deletePlayer(@PathVariable("id") Long id) {
        if(id <= 0 || !(id instanceof Long) )
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (!(playerService.existsById(id)))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        playerService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getPlayersCount(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "3") Integer pageSize,
            @RequestParam(required = false, defaultValue = "ID") PlayerOrder order,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam(required = false, defaultValue = "") Race race,
            @RequestParam(required = false, defaultValue = "") Profession profession,
            @RequestParam(required = false, defaultValue = "") Boolean banned,
            @RequestParam(required = false, defaultValue = "") Long after,
            @RequestParam(required = false, defaultValue = "") Long before,
            @RequestParam(required = false, defaultValue = "") Integer minExperience,
            @RequestParam(required = false, defaultValue = "") Integer maxExperience,
            @RequestParam(required = false, defaultValue = "") Integer minLevel,
            @RequestParam(required = false, defaultValue = "") Integer maxLevel
    )  {
        Sort sort = null;
        switch (order) {
            case ID:
                sort = Sort.by("id");
                break;
            case NAME:
                sort = Sort.by("name");
                break;
            case EXPERIENCE:
                sort = Sort.by("experience");
                break;
            case BIRTHDAY:
                sort = Sort.by("birthday");
                break;
        }
        PageRequest pageRequest = PageRequest.of(pageNumber,pageSize,sort);
        Page<Player> players = playerService.searchByCriterias(name,title,race,profession, banned, after, before,
                minExperience, maxExperience, minLevel, maxLevel, pageRequest);



        return new ResponseEntity<>((int)players.getTotalElements(), HttpStatus.OK);
    }
}