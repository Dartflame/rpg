package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import com.game.utils.PlayerSearchSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    PlayerRepository repository;

    @Autowired
    public void setRepository(PlayerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Player getById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void save(Player player) throws Exception {
        if (player == null
                || player.getName() == null
                || player.getTitle() == null
                || player.getRace() == null
                || player.getProfession() == null
                || player.getName().equals("")
                || player.getExperience() == null
                || player.getBirthday() == null
                || player.getExperience() < 0
                || player.getExperience() > 10_000_000
                || player.getBirthday().getTime() <0l
                || player.getTitle().length() > 30
                || player.getName().length() > 12
                || player.getBirthday().before(new Date(100,1,1))
                || player.getBirthday().after(new Date(1100,1,1))) {
            throw new Exception();
        }
        if(player.isBanned() == null)
            player.setBanned(false);

        player.setLevel((int) ((Math.sqrt(2500 + 200 * player.getExperience())- 50) / 100));
        player.setUntilNextLevel(50 * (player.getLevel() + 1) * (player.getLevel() + 2) - player.getExperience());
        repository.save(player);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Player> getAll() {
        return (List<Player>) repository.findAll();
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public Player update(Player player, Long id) {
        Player entity = null;
        if (id == 0)
            return new Player();
        if(existsById(id))
            entity = repository.findById(id).get();
        else return entity;

        if(player.getExperience() != null) {

            if(player.getExperience() > 10_000_00 || player.getExperience() < 0)
                return new Player();

            entity.setExperience(player.getExperience());
            double di = (Math.sqrt(2500 + 200 * player.getExperience())- 50) / 100;
            int i = (int) di;
            entity.setLevel(i);
            entity.setUntilNextLevel(50 * (entity.getLevel() + 1) * (entity.getLevel() + 2) - entity.getExperience());
        }
        if(player.getName() != null) entity.setName(player.getName());
        if(player.getTitle() != null) entity.setTitle(player.getTitle());
        if(player.getRace() != null) entity.setRace(player.getRace());
        if(player.getProfession() != null) entity.setProfession(player.getProfession());

        if(player.getBirthday() != null) {
            if(player.getBirthday().getTime() <0l)
                return new Player();

            entity.setBirthday(player.getBirthday());
        }

        if(player.isBanned() != null) entity.setBanned(player.isBanned());

        return repository.save(entity);
    }

    @Override
    public Page<Player> searchByCriterias(String name, String title,
                                          Race race, Profession profession,
                                          Boolean banned,
                                          Long after, Long before,
                                          Integer experienceMin,
                                          Integer experienceMax,
                                          Integer levelMin,
                                          Integer levelMax,
                                          Pageable pageable
    ) {

        return repository.findAll(PlayerSearchSpecs.findPlayerByName(name)
                .and(PlayerSearchSpecs.findPlayerByRace(race))
                .and(PlayerSearchSpecs.findPlayerByProfession(profession))
                .and(PlayerSearchSpecs.findPlayerByStatus(banned))
                .and(PlayerSearchSpecs.findPlayerByTitle(title))
                .and(PlayerSearchSpecs.findPlayerByDate(after,before))
                .and(PlayerSearchSpecs.findPlayerByExp(experienceMin, experienceMax))
                .and(PlayerSearchSpecs.findPlayerByMaxLevel(levelMax))
                .and(PlayerSearchSpecs.findPlayerByMinLevel(levelMin)),pageable);
    }

}
