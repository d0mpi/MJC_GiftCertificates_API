package com.epam.esm.util.searcher;

import com.epam.esm.Certificate;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Map;

/**
 * Makes it easier to write a string containing SQL query
 * for searching entity in the database based on the received parameters.
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 */
public interface EntityQueryBuilder {
    TypedQuery<Certificate> getQuery(Map<String, String> paramMap, EntityManager entityManager);
}
