package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

public class PlayerCriterias {

    public static Specification<Player> findPlayerByName(final String name) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root,
                                         CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {

                return criteriaBuilder.like(root.get("name"), "%"+name+"%");
            }
        };
    }
    public static Specification<Player> findPlayerByTitle(final String title) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root,
                                         CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {

                return criteriaBuilder.like(root.get("title"), "%"+title+"%");
            }
        };
    }
    public static Specification<Player> findPlayerByRace(final Race race) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root,
                                         CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                if(race == null)
                    return null;
                return criteriaBuilder.equal(root.get("race"), race);
            }
        };
    }
    public static Specification<Player> findPlayerByProfession(final Profession profession) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root,
                                         CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {

                if(profession == null)
                    return null;
                return criteriaBuilder.equal(root.get("profession"), profession);
            }
        };
    }
    public static Specification<Player> findPlayerByStatus(final Boolean banned) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root,
                                         CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                if(banned == null)
                    return null;
                return criteriaBuilder.equal(root.get("banned"), banned);
            }
        };
    }

    public static Specification<Player> findPlayerByDate(final Long after, final Long before) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root,
                                         CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {

                Predicate greater = null;
                Predicate lesser = null;

                if(after == null && before != null){
                    lesser = criteriaBuilder.lessThanOrEqualTo(root.get("birthday"),new Date(before));
                    return lesser;
                }
                if(after != null && before == null){
                    greater = criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"),new Date(after));
                    return greater;
                }

                if(after != null && before != null){
                    greater = criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"),new Date(after));
                    lesser = criteriaBuilder.lessThanOrEqualTo(root.get("birthday"),new Date(before));
                    return criteriaBuilder.and(greater,lesser);
                }

                return null;
            }
        };
    }
    public static Specification<Player> findPlayerByExp(final Integer experienceMin,final Integer experienceMax) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root,
                                         CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {

                Predicate lesser = null;
                Predicate greater = null;

                if(experienceMin == null && experienceMax != null){
                    lesser = criteriaBuilder.lessThanOrEqualTo(root.get("experience"),experienceMax);
                    return lesser;
                }
                if(experienceMin != null && experienceMax == null){
                    greater = criteriaBuilder.greaterThanOrEqualTo(root.get("experience"),experienceMin);
                    return greater;
                }
                if(experienceMax != null && experienceMin != null) {
                    greater = criteriaBuilder.greaterThanOrEqualTo(root.get("experience"), experienceMin);
                    lesser = criteriaBuilder.lessThanOrEqualTo(root.get("experience"), experienceMax);
                    return criteriaBuilder.and(greater, lesser);
                }

                return null;
            }
        };
    }
    public static Specification<Player> findPlayerByMinLevel(final Integer levelMin) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root,
                                         CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {

                if(levelMin == null)
                    return null;

                Predicate greater = criteriaBuilder.greaterThanOrEqualTo(root.get("level"),levelMin);
                return greater;
            }
        };
    }
    public static Specification<Player> findPlayerByMaxLevel(final Integer maxLevel) {
        return new Specification<Player>() {
            @Override
            public Predicate toPredicate(Root<Player> root,
                                         CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {

                if(maxLevel == null)
                    return null;

                Predicate lesser = criteriaBuilder.lessThanOrEqualTo(root.get("level"),maxLevel);
                return lesser;
            }
        };
    }
//    public static Specification<Player> findPlayerByLevel(final Integer levelMin,final Integer levelMax) {
//        return new Specification<Player>() {
//            @Override
//            public Predicate toPredicate(Root<Player> root,
//                                         CriteriaQuery<?> criteriaQuery,
//                                         CriteriaBuilder criteriaBuilder) {
//
//                if(levelMin == null & levelMax == null)
//                    return null;
//
//                Predicate lesser = null;
//                Predicate greater = null;
//
//                if(levelMin == null && levelMax != null){
//                    lesser = criteriaBuilder.lessThanOrEqualTo(root.get("level"),levelMax);
//                    return lesser;
//                }
//                if(levelMin != null && levelMax == null){
//                    greater = criteriaBuilder.greaterThanOrEqualTo(root.get("level"),levelMin);
//                    return greater;
//                }
//
//                greater = criteriaBuilder.greaterThanOrEqualTo(root.get("level"),levelMin);
//                lesser = criteriaBuilder.lessThanOrEqualTo(root.get("level"),levelMax);
//                return criteriaBuilder.and(greater,lesser);
//            }
//        };
//    }

}
