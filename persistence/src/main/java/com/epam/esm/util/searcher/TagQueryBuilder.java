package com.epam.esm.util.searcher;

import org.springframework.stereotype.Component;

import java.util.Map;


/**
 * Class makes it easier to write a string containing SQL query
 * for searching tags in the database based on the received parameters.
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 * @see EntityQueryBuilder
 */
@Component
public class TagQueryBuilder implements EntityQueryBuilder {

    /**
     * Creates a query to the database for searching
     * certificates in parts based on the received parameters
     *
     * @param paramMap map of parameters received from web
     * @return a ready-to-use string with SQL query for searching certificates
     * with specified parameters
     */
    @Override
    public String getQuery(Map<String, String> paramMap) {
        return "select * from tag";
    }

    private TagQueryBuilder() {
    }


    /**
     * Replacement of the constructor for ease of use
     *
     * @return new instance of the class
     */
    public static TagQueryBuilder init() {
        return new TagQueryBuilder();
    }
}
