package com.game.repository;

import com.game.entity.Player;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.List;


@Repository
@Transactional
public interface PlayerRepository extends PagingAndSortingRepository<Player,Long>, JpaSpecificationExecutor<Player> {
    boolean existsById(Long id);

    List<Player> findAllByNameContainsAndTitleContains(String name, String title, Pageable pageable);

    //Specification<Player> findAllByLevelBeforeAndLevelAfter(Integer maxLevel,Integer minLevel, Pageable pageable);
    //List<Player> findAllByLevelBeforeAndLevelAfter(Integer maxLevel,Integer minLevel, Pageable pageable);

    //Specification<Player> findAllByLevelBeforeAndLevelAfter(Integer maxLevel,Integer minLevel, Pageable pageable);
    //Iterable<Player> findAll(String name, Pageable pageable, Specification<Player> predicate);
    //List<Player> findAllByTitleContains(String title, Pageable pageable);

}