package com.game.service;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface PlayerService {
    Player getById(Long id);

    void save(Player player) throws Exception;

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

}
