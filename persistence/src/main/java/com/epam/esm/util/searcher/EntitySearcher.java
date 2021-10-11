package com.epam.esm.util.searcher;

import java.util.Map;

/**
 * Makes it easier to write a string containing SQL query
 * for searching entity in the database based on the received parameters.
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 */
public interface EntitySearcher {
    String getQuery(Map<String, String> paramMap);
}
