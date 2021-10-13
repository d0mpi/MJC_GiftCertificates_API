package com.epam.esm.util.searcher;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * Class makes it easier to write a string containing SQL query
 * for searching certificates in the database based on the received parameters.
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 * @see EntityQueryBuilder
 */
@Component
public class CertificateQueryBuilder implements EntityQueryBuilder {
    private final StringBuilder joinQuery;
    private final StringBuilder sortingQuery;
    private final StringBuilder filteringQuery;

    private CertificateQueryBuilder() {
        this.sortingQuery = new StringBuilder();
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
    public String getQuery(Map<String, String> paramMap) {
        for (Map.Entry<String, String> param : paramMap.entrySet()) {
            if (CertificateCriteriaStorage.hasParam(param.getKey())) {
                addQueryComponent(Objects.requireNonNull(CertificateCriteriaStorage.of(param.getKey())), param.getValue());
            }
        }
        return "select certificate.id," +
                " certificate.name," +
                " certificate.description," +
                " certificate.price," +
                " certificate.duration," +
                " certificate.create_date," +
                " certificate.last_update_date " +
                " from certificate " + joinQuery + filteringQuery + sortingQuery;
    }

    private void addQueryComponent(CertificateCriteriaStorage certificateFindParam, String paramValue) {
        if (certificateFindParam.equals(CertificateCriteriaStorage.TAG)) {
            joinQuery.append(" join certificate_tag " +
                    "              on certificate.id = certificate_tag.certificate_id" +
                    "         join tag " +
                    "              on certificate_tag.tag_id = tag.id ");
        }
        if (certificateFindParam.equals(CertificateCriteriaStorage.SORT)) {
            Arrays.stream(paramValue.split("[,+\\s]"))
                    .forEach(component -> addSortComponent(certificateFindParam, component));
        } else {
            addFilterComponent(certificateFindParam, paramValue);
        }
    }

    private void addSortComponent(CertificateCriteriaStorage certificateFindParam, String sortComponent) {
        if (this.sortingQuery.length() == 0) {
            this.sortingQuery.append(" order by ");
        } else {
            this.sortingQuery.append(" , ");
        }
        this.sortingQuery.append(certificateFindParam.component(sortComponent));
    }

    private void addFilterComponent(CertificateCriteriaStorage certificateFindParam, String filterComponent) {
        this.filteringQuery.append(" and ");
        this.filteringQuery.append(certificateFindParam.component(filterComponent));
    }
}
