package com.epam.esm.util.searcher;

import com.epam.esm.Certificate;

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
        Set<String> tagNames = new HashSet<>();
        Expression<Long> count = null;
        for (Map.Entry<String, String> param : paramMap.entrySet()) {
            if (CertificateCriteriaStorage.hasParam(param.getKey())) {
                if (Objects.equals(CertificateCriteriaStorage.of(param.getKey()), CertificateCriteriaStorage.TAGS)) {
                    tagNames = new HashSet<>(Arrays.asList(param.getValue().split(",")));
                    Join<Object, Object> certificateTagJoin = root.join("tags", JoinType.LEFT);
                    count = cb.count(root);
                    predicates.add(cb.and(certificateTagJoin.get("name").in(tagNames)));
                    findByTags = true;
                } else {
                    predicates.add(Objects.requireNonNull(CertificateCriteriaStorage.of(param.getKey())).component(param.getValue(),
                            cq, cb, root));
                }
            }
        }
        if (findByTags) {
            return entityManager.createQuery(cq.where(cb.and(predicates.toArray(new Predicate[0]))).groupBy(root)
                    .having(cb.equal(count, tagNames.size())));
        }

        return entityManager.createQuery(cq.where(predicates.toArray(new Predicate[0])));
    }


//    private void addQueryComponent(CertificateCriteriaStorage certificateFindParam, String paramValue) {
//        if (certificateFindParam.equals(CertificateCriteriaStorage.TAG)) {
//            joinQuery.append(" join certificate_tag " +
//                    "              on certificate.id = certificate_tag.certificate_id" +
//                    "         join tag " +
//                    "              on certificate_tag.tag_id = tag.id ");
//        }
//        if (certificateFindParam.equals(CertificateCriteriaStorage.SORT)) {
//            Arrays.stream(paramValue.split("[,+\\s]"))
//                    .forEach(component -> addSortComponent(certificateFindParam, component));
//        } else {
//            addFilterComponent(certificateFindParam, paramValue);
//        }
//    }

//    private void addSortComponent(CertificateCriteriaStorage certificateFindParam, String sortComponent) {
//
//        if (this.sortingQuery.length() == 0) {
//            this.sortingQuery.append(" order by ");
//        } else {
//            this.sortingQuery.append(" , ");
//        }
////        if(sortComponent.contains("date_asc")) {
////            sortingQuery.append(" certificate.last_update_date ASC");
////        } else if(sortComponent.contains("date_desc")) {
////            sortingQuery.append(" certificate.last_update_date DESC");
////        } else if(sortComponent.contains("name_asc")) {
////            sortingQuery.append(" certificate.name ASC");
////        }
//        this.sortingQuery.append(certificateFindParam.component(sortComponent));
//    }
//
//    private void addFilterComponent(CertificateCriteriaStorage certificateFindParam, String filterComponent) {
//        this.filteringQuery.append(" and ");
//        this.filteringQuery.append(certificateFindParam.component(filterComponent));
//    }
}
