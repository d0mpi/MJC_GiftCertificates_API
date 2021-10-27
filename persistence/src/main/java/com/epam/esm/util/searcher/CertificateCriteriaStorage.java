package com.epam.esm.util.searcher;

import com.epam.esm.Certificate;
import lombok.Getter;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * Class store all possible url parameters for certificate search and
 * query components corresponding to these parameters.
 * Each name of the constant equals ignore case to one parameter name.
 * Each constant contains method component() which return string with SQL query component.
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 * @see EntityQueryBuilder, CertificateQueryBuilder, TagQueryBuilder
 */
public enum CertificateCriteriaStorage {
    NAME {
        @Override
        public Predicate component(String value,
                                   CriteriaQuery<Certificate> cq,
                                   CriteriaBuilder cb,
                                   Root<Certificate> root) {
            return cb.and(cb.like(cb.upper(root.get("name")), "%" + value + "%"));
        }
    },
    DESCRIPTION {
        @Override
        public Predicate component(String value,
                                   CriteriaQuery<Certificate> cq,
                                   CriteriaBuilder cb,
                                   Root<Certificate> root) {
            return cb.and(cb.like(cb.upper(root.get("description")), "%" + value + "%"));
        }
    },
    PRICE {
        @Override
        public Predicate component(String value,
                                   CriteriaQuery<Certificate> cq,
                                   CriteriaBuilder cb,
                                   Root<Certificate> root) {
            return cb.and(cb.equal(root.get("price"), value));
        }
    },
    TAGS {
        @Override
        public Predicate component(String value,
                                   CriteriaQuery<Certificate> cq,
                                   CriteriaBuilder cb,
                                   Root<Certificate> root) {
            Set<String> tagNames = new HashSet<>(Arrays.asList(value.split(",")));
            Join<Object, Object> certificateTagJoin = root.join("tags", JoinType.LEFT);
            return cb.and(certificateTagJoin.get("name").in(tagNames));
        }
    },
    SORT {
        @Override
        public Predicate component(String value, CriteriaQuery<Certificate> query, CriteriaBuilder criteriaBuilder, Root<Certificate> root) {
            return null;
        }
    };

    /**
     * Checks is parameter with the specified name present in this enum
     *
     * @param param name of the param to be checked
     * @return true - if parameter is present
     */
    static boolean hasParam(String param) {
        for (CertificateCriteriaStorage type : values()) {
            if (type.name().equalsIgnoreCase(param)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds CertificateParam corresponding to the specified param name
     *
     * @param param name of the parameter to be found
     * @return CertificateParam corresponding to the specified param name
     */
    static CertificateCriteriaStorage of(String param) {
        if (hasParam(param))
            return CertificateCriteriaStorage.valueOf(param.toUpperCase(Locale.ROOT));
        else
            return null;
    }

    /**
     * Formats and returns string with SQL query component.
     *
     * @param value url parameter received from request
     * @return component of SQL Query
     */
    public abstract Predicate component(String value,
                                        CriteriaQuery<Certificate> query,
                                        CriteriaBuilder criteriaBuilder,
                                        Root<Certificate> root);

    /**
     * Nested enum containing all possible types of sorting
     */
    public enum SortType {
        NAME_ASC("name_asc", "a-z", "aname") {
            @Override
            public Order component(CriteriaQuery<Certificate> cq,
                                   CriteriaBuilder cb,
                                   Root<Certificate> root) {
                return cb.asc(root.get("name"));
            }
        },
        NAME_DESC("name_desc", "z-a", "dname") {
            @Override
            public Order component(CriteriaQuery<Certificate> cq,
                                   CriteriaBuilder cb,
                                   Root<Certificate> root) {
                return cb.desc(root.get("name"));
            }
        },
        DATE_ASC("date_asc", "adate", "newest") {
            @Override
            public Order component(CriteriaQuery<Certificate> cq,
                                   CriteriaBuilder cb,
                                   Root<Certificate> root) {
                return cb.asc(root.get("lastUpdateDate"));
            }
        },
        DATE_DESC("date_desc", "ddate", "oldest") {
            @Override
            public Order component(CriteriaQuery<Certificate> cq,
                                   CriteriaBuilder cb,
                                   Root<Certificate> root) {
                return cb.desc(root.get("lastUpdateDate"));
            }
        };

        public abstract Order component(CriteriaQuery<Certificate> query,
                                        CriteriaBuilder criteriaBuilder,
                                        Root<Certificate> root);

        @Getter
        final List<String> paramVars;

        SortType(String... paramVars) {
            this.paramVars = Arrays.asList(paramVars);
        }

        /**
         * Finds SortingType corresponding to the specified param name
         *
         * @param param name of the parameter to be found
         * @return SortType corresponding to the specified param name
         */
        static Optional<SortType> of(String param) {
            for (SortType type : values()) {
                if (type.getParamVars().contains(param)) {
                    return Optional.of(type);
                }
            }
            return Optional.empty();
        }
    }
}
