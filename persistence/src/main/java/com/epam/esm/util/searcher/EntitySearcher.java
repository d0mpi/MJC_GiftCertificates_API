package com.epam.esm.util.searcher;

import java.util.Map;

public interface EntitySearcher {
    String getQuery(Map<String, String> paramMap);
}
