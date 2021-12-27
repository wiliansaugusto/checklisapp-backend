package com.learning.api.checklistappapi.dto.ChecklistItemDTO;

import com.learning.api.checklistappapi.entity.CategoryEntity;
import com.learning.api.checklistappapi.entity.ChecklistItemEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class ChecklistItemItemDTO {

    private String guid;
    private Boolean isCompleted;
    private LocalDate deadline;
    private LocalDate postDate;
    private String categoryGuid;
    private String description;
    private CategoryEntity category;

    public static Object toDTO(ChecklistItemEntity checklistItemEntity) {
        return ChecklistItemItemDTO.builder()
                .guid(checklistItemEntity.getGuid())
                .isCompleted(checklistItemEntity.getIsCompleted())
                .deadline(checklistItemEntity.getDeadline())
                .postDate(checklistItemEntity.getPostedDate())
                .categoryGuid(checklistItemEntity.getCategory().getGuid())
                .description(checklistItemEntity.getDescription())
                .build();


    }
}
