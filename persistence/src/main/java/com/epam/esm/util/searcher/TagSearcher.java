package com.epam.esm.util.searcher;

import java.util.Map;

public class TagSearcher implements EntitySearcher{

    @Override
    public String getQuery(Map<String, String> paramMap) {
        return "select * from tag";
    }
}
