package com.epam.esm.util.searcher;

import com.epam.esm.Certificate;
import org.springframework.data.jpa.repository.query.InvalidJpaQueryMethodException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;

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
        boolean findByTags = false;
        Set<String> tagNames = null;
        Expression<Long> count = null;
        List<Order> orderList = new LinkedList<>(Arrays.asList(cb.desc(root.get("lastUpdateDate"))));
        for (Map.Entry<String, String> param : paramMap.entrySet()) {
            if (CertificateCriteriaStorage.hasParam(param.getKey())) {
                CertificateCriteriaStorage criteria = CertificateCriteriaStorage.of(param.getKey());
                if (Objects.equals(criteria, CertificateCriteriaStorage.TAGS)) {
                    findByTags = true;
                    tagNames = new HashSet<>(Arrays.asList(param.getValue().split(",")));
                    predicates.add(criteria.component(param.getValue(), cq, cb, root));
                    count = cb.count(root);
                } else if (Objects.equals(criteria, CertificateCriteriaStorage.SORT)) {
                    orderList.clear();
                    Set<String> values = new HashSet<>(Arrays.asList(param.getValue().split(",")));
                    for (String sortType : values) {
                        orderList.add(CertificateCriteriaStorage.SortType.of(sortType)
                                .orElseThrow(() -> new InvalidJpaQueryMethodException("message.not-valid.query.order"))
                                .component(cq, cb, root));
                    }
                } else {
                    predicates.add(Objects.requireNonNull(CertificateCriteriaStorage.of(param.getKey()))
                            .component(param.getValue(), cq, cb, root));
                }
            }
        }
        if (findByTags) {
            return entityManager.createQuery(cq.where(cb.and(predicates.toArray(new Predicate[0]))).groupBy(root)
                    .having(cb.equal(count, tagNames.size())).orderBy(orderList));
        }
        return entityManager.createQuery(cq.where(cb.and(predicates.toArray(new Predicate[0]))).orderBy(orderList));
    }

}
