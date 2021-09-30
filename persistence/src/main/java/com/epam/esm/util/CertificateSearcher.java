package com.epam.esm.util;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class CertificateSearcher {
    private String sortingQuery;
    private String filteringQuery;

    public String getQuery(Map<String, String> paramMap) {
        return "select * from certificate";
    }

    public void addQueryComponent(Map.Entry<String, String> param) {
    }
}
