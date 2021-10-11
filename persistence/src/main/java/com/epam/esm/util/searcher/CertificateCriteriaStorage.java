package com.epam.esm.util.searcher;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
        public String component(String value) {
            return String.format(" certificate.name LIKE '%%%s%%' ", value);
        }
    },
    DESCRIPTION {
        @Override
        public String component(String value) {
            return String.format(" certificate.description LIKE '%%%s%%' ", value);
        }
    },
    PRICE {
        @Override
        public String component(String value) {
            return String.format(" certificate.price = %s ", value);
        }
    },
    TAG {
        @Override
        public String component(String value) {
            return String.format(" tag.name = '%s' ", value);
        }
    },
    SORT {
        @Override
        public String component(String value) {
            return SortType.of(value).getQueryComponent();
        }
    };

    /**
     * Formats and returns string with SQL query component.
     *
     * @param value url parameter received from request
     * @return component of SQL Query
     */
    public abstract String component(String value);

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
     * Nested enum containing all possible types of sorting
     */
    public enum SortType {
        NAME_ASC(" certificate.name ASC ", "name_asc", "a-z", "aname"),
        NAME_DESC(" certificate.name DESC ", "name_desc", "z-a", "dname"),
        DATE_ASC(" certificate.last_update_date ASC ", "date_asc", "adate", "newest"),
        DATE_DESC(" certificate.last_update_date DESC ", "date_desc", "ddate", "oldest");

        @Getter
        final String queryComponent;
        @Getter
        final List<String> paramVars;

        SortType(String queryComponent, String... paramVars) {
            this.queryComponent = queryComponent;
            this.paramVars = Arrays.asList(paramVars);
        }

        /**
         * Finds SortingType corresponding to the specified param name
         *
         * @param param name of the parameter to be found
         * @return SortType corresponding to the specified param name
         */
        static SortType of(String param) {
            for (SortType type : values()) {
                if (type.getParamVars().contains(param)) {
                    return type;
                }
            }
            return DATE_DESC;
        }
    }
}
