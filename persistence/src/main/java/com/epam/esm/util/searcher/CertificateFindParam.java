package com.epam.esm.util.searcher;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public enum CertificateFindParam {
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

    public abstract String component(String value);

    static boolean hasParam(String param) {
        for (CertificateFindParam type : values()) {
            if (type.name().equalsIgnoreCase(param)) {
                return true;
            }
        }
        return false;
    }

    static CertificateFindParam of(String param) {
        if (hasParam(param))
            return CertificateFindParam.valueOf(param.toUpperCase(Locale.ROOT));
        else
            return null;
    }

    public enum SortType {
        NAME_ASC(" certificate.name ASC ", "name_asc", "name,asc", "a-z", "aname"),
        NAME_DESC(" certificate.name DESC ", "name_desc", "name,desc", "z-a", "dname"),
        DATE_ASC(" certificate.create_date ASC ", "date_asc", "date,asc", "adate", "newest"),
        DATE_DESC(" certificate.create_date DESC ", "date_desc", "date,desc", "ddate", "oldest");

        @Getter
        final String queryComponent;
        @Getter
        final List<String> paramVars;

        SortType(String queryComponent, String... paramVars) {
            this.queryComponent = queryComponent;
            this.paramVars = Arrays.asList(paramVars);
        }

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
