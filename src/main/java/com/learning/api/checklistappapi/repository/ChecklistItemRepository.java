package com.learning.api.checklistappapi.repository;

import com.learning.api.checklistappapi.entity.ChecklistItemEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChecklistItemRepository extends PagingAndSortingRepository<ChecklistItemEntity, Long> {
    Optional<ChecklistItemEntity> findByGuid(String guid);

    List<ChecklistItemEntity> findByDescriptionAndIsCompleted(String description, Boolean isCompleted);
    List<ChecklistItemEntity> findByCategoryName(String guid);
}
