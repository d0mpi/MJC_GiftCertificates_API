package com.epam.esm.repository;

import com.epam.esm.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends Repository<Tag> {
    List<Tag> findTagsByCertificateId(long id);

    Optional<Tag> isPresent(String name);
}
