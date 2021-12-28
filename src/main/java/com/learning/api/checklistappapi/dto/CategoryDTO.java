package com.learning.api.checklistappapi.dto;


import com.learning.api.checklistappapi.entity.CategoryEntity;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class CategoryDTO {

    private String guid;
    @NotBlank(message = "Nome da categoria não pode ser nula ou vazia")
    private String name;

    public static CategoryDTO toDTO(CategoryEntity categoryEntity) {
        return CategoryDTO.builder()
                .guid(categoryEntity.getGuid())
                .name(categoryEntity.getName())
                .build();
    }

}
