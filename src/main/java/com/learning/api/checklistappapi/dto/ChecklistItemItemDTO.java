package com.learning.api.checklistappapi.dto;

import com.learning.api.checklistappapi.entity.CategoryEntity;
import com.learning.api.checklistappapi.entity.ChecklistItemEntity;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@Getter
public class ChecklistItemItemDTO {

    private String guid;
    @NotNull(message = "Checagem Obrigatoria é obrigatorio")
    private Boolean isCompleted;
    @NotNull(message = "Deadline é obrigatorio")
    private LocalDate deadline;
    private LocalDate postDate;
    private String categoryGuid;
    @NotBlank(message = "Descrição nao pode ser nula ou vazia")
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
