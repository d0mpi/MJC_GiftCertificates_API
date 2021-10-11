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
 * @see EntitySearcher
 */
@Component
public class CertificateSearcher implements EntitySearcher {
    private final StringBuilder joinQuery;
    private final StringBuilder sortingQuery;
    private final StringBuilder filteringQuery;

    private CertificateSearcher() {
        this.sortingQuery = new StringBuilder();
        this.filteringQuery = new StringBuilder();
        this.joinQuery = new StringBuilder();
    }

    /**
     * Replacement of the constructor for ease of use
     *
     * @return new instance of the class
     */
    public static CertificateSearcher init() {
        return new CertificateSearcher();
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
            if (CertificateFindParam.hasParam(param.getKey())) {
                addQueryComponent(Objects.requireNonNull(CertificateFindParam.of(param.getKey())), param.getValue());
            }
        }
        System.out.println("select * from certificate " + filteringQuery + sortingQuery);
        return "select certificate.id," +
                " certificate.name," +
                " certificate.description," +
                " certificate.price," +
                " certificate.duration," +
                " certificate.create_date," +
                " certificate.last_update_date " +
                " from certificate " + joinQuery + filteringQuery + sortingQuery;
    }

    private void addQueryComponent(CertificateFindParam certificateFindParam, String paramValue) {
        if (certificateFindParam.equals(CertificateFindParam.TAG)) {
            joinQuery.append(" join certificate_tag " +
                    "              on certificate.id = certificate_tag.certificate_id" +
                    "         join tag " +
                    "              on certificate_tag.tag_id = tag.id ");
        }
        if (certificateFindParam.equals(CertificateFindParam.SORT)) {
            Arrays.stream(paramValue.split("[,+\\s]"))
                    .forEach(component -> addSortComponent(certificateFindParam, component));
        } else {
            addFilterComponent(certificateFindParam, paramValue);
        }
    }

    private void addSortComponent(CertificateFindParam certificateFindParam, String sortComponent) {
        System.out.println(sortComponent);
        if (this.sortingQuery.isEmpty()) {
            this.sortingQuery.append(" order by ");
        } else {
            this.sortingQuery.append(" , ");
        }
        this.sortingQuery.append(certificateFindParam.component(sortComponent));
    }

    private void addFilterComponent(CertificateFindParam certificateFindParam, String filterComponent) {
        if (this.filteringQuery.isEmpty()) {
            this.filteringQuery.append(" where ");
        } else {
            this.filteringQuery.append(" and ");
        }
        this.filteringQuery.append(certificateFindParam.component(filterComponent));
    }
}
