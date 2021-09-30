package com.epam.esm.repository;

import com.epam.esm.Tag;

import java.util.List;

public interface TagRepository extends Repository<Tag> {
    List<Tag> findTagsByCertificateId(long id);
}
