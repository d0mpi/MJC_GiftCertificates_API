package com.epam.esm.util;

import java.util.Map;

public class TagSearcher {

    public String getQuery(Map<String, String> paramMap) {
        return "select * from tag";
    }
}
