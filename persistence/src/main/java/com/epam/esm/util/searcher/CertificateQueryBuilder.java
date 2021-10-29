package com.epam.esm.util.searcher;

import com.epam.esm.Certificate;
import com.epam.esm.exception.InvalidQueryParamException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class makes it easier to write a string containing SQL query
 * for searching certificates in the database based on the received parameters.
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 * @see EntityQueryBuilder
 */
public class CertificateQueryBuilder implements EntityQueryBuilder {
    private final StringBuilder joinQuery;
    private final StringBuilder sortingQuery;
    private final StringBuilder filteringQuery;
    private final StringBuilder limitQuery;

    private CertificateQueryBuilder() {
        this.sortingQuery = new StringBuilder();
        this.limitQuery = new StringBuilder();
        this.filteringQuery = new StringBuilder(" where true ");
        this.joinQuery = new StringBuilder();
    }

    /**
     * Replacement of the constructor for ease of use
     *
     * @return new instance of the class
     */
    public static CertificateQueryBuilder init() {
        return new CertificateQueryBuilder();
    }

    /**
     * Creates a query to the database for searching
     * certificates in parts based on the received parameters
     *
     * @param paramMap map of parameters received from web
     * @return a ready-to-use string with SQL query for searching certificates
     * with specified parameters
     */
    @Override
    public TypedQuery<Certificate> getQuery(Map<String, String> paramMap, EntityManager entityManager) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Certificate> cq = cb.createQuery(Certificate.class);
        Root<Certificate> root = cq.from(Certificate.class);
        cq = cq.select(root);

        List<Predicate> predicates = new ArrayList<>();
        List<Order> orderList = new LinkedList<>(List.of(cb.desc(root.get("lastUpdateDate"))));

        for (Map.Entry<String, String> param : paramMap.entrySet()) {
            CertificateCriteriaStorage criteria = CertificateCriteriaStorage.of(param.getKey())
                    .orElseThrow(() -> new InvalidQueryParamException("message.query-param"));
            switch (criteria) {
                case TAGS -> {
                    Set<String> tagNames = new HashSet<>(Arrays.asList(param.getValue().split(",")));
                    predicates.add(criteria.component(param.getValue(), cq, cb, root));
                    cq.groupBy(root).having(cb.equal(cb.count(root), tagNames.size()));
                }
                case SORT -> {
                    orderList.clear();
                    Set<String> values = new LinkedHashSet<>(Arrays.asList(param.getValue().split(",")));
                    for (String sortType : values) {
                        orderList.add(CertificateCriteriaStorage.SortType.of(sortType)
                                .orElseThrow(() -> new InvalidQueryParamException("message.not-valid.query.order"))
                                .component(cq, cb, root));
                    }
                }
                default -> predicates.add(criteria.component(param.getValue(), cq, cb, root));
            }
        }
        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        cq.orderBy(orderList);
        return entityManager.createQuery(cq);
    }
}
