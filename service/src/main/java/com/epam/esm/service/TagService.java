package com.epam.esm.service;

import com.epam.esm.DTO.TagDTO;
import com.epam.esm.Tag;

/**
 * Provides tag info transfer between web and persistence module,
 * required to convert tag info from {@link TagDTO}
 * to {@link Tag} and vice-versa. Is necessary for performing
 * intermediate actions on tag info (verifying, updating data etc.)
 *
 * @author Mikhail Dokuchaev
 * @version 1.0
 * @see com.epam.esm.repository.TagRepository
 * @see EntityService
 */
public interface TagService extends EntityService<TagDTO> {
    TagDTO getMostWidelyUsedTag(long userId);
}
