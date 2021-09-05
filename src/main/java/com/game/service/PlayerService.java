package com.game.service;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public interface PlayerService {
    Player getById(Long id);

    void save(Player player);

    void delete(Long id);

    List<Player> getAll();


    boolean existsById(Long id);

    Player update(Player player, Long id);

    Page<Player> searchByCriterias(String name, String title,
                                   Race race, Profession profession,
                                   Boolean banned,
                                   Long after, Long before,
                                   Integer experienceMin, Integer experienceMax,
                                   Integer levelMin, Integer levelMax,
                                   Pageable pageable);

    //List<Player> findAllByLevelBeforeAndLevelAfter(Integer maxLevel, Integer minLevel, Pageable pageable);


}