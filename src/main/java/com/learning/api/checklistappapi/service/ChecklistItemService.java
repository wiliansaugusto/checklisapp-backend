package com.learning.api.checklistappapi.service;

import com.learning.api.checklistappapi.dto.CategoryDTO;
import com.learning.api.checklistappapi.entity.CategoryEntity;
import com.learning.api.checklistappapi.entity.ChecklistItemEntity;
import com.learning.api.checklistappapi.exception.ResourceNotFoundException;
import com.learning.api.checklistappapi.repository.CategoryRepository;
import com.learning.api.checklistappapi.repository.ChecklistItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
public class ChecklistItemService {
    @Autowired
    private ChecklistItemRepository checklistItemRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private void validChecklistItes(String description,
                                    String categoryGuid,
                                    Boolean isCompleted,
                                    LocalDate deadline) {
        if (!StringUtils.hasText(description)) {
            throw new IllegalArgumentException("Descrição esta com valor nullo ou vazio");
        }
        if (isCompleted == null) {
            throw new IllegalArgumentException("Checklist flag não foi selecionada");
        }
        if (deadline == null) {
            throw new IllegalArgumentException("Checklist deadline tem valor nulo ou vazio");
        }
        if (!StringUtils.hasText(categoryGuid)) {
            throw new IllegalArgumentException("categoryGuid tem valor nulo ou vazio");
        }

    }

    public ChecklistItemEntity addNewChecklistItem(String description,
                                                   String categoryGuid,
                                                   Boolean isCompleted,
                                                   LocalDate deadline) {
        this.validChecklistItes(description, categoryGuid, isCompleted, deadline);
        CategoryEntity retrivedCategory = this.categoryRepository.findByGuid(categoryGuid).orElseThrow(
                () -> new ResourceNotFoundException("Categoria não encontrada")
        );
        ChecklistItemEntity checklistItemEntity = new ChecklistItemEntity();
        checklistItemEntity.setGuid(UUID.randomUUID().toString());
        checklistItemEntity.setDescription(description);
        checklistItemEntity.setDeadline(deadline);
        checklistItemEntity.setPostedDate(LocalDate.now());
        checklistItemEntity.setCategory(retrivedCategory);
        checklistItemEntity.setIsCompleted(isCompleted);
        log.debug("Adiconado novo item no checjlist [checklistItem = {}]", checklistItemEntity);
        return this.checklistItemRepository.save(checklistItemEntity);

    }

    public Iterable<ChecklistItemEntity> findAllCheklistItems() {
        return this.checklistItemRepository.findAll();
    }


    public void deleteChecllistItems(String guid) {
        if (!StringUtils.hasText(guid)) {
            throw new IllegalArgumentException("Categoria sendo usado por outros items");
        }
        ChecklistItemEntity retriveItem = this.checklistItemRepository.findByGuid(guid).orElseThrow(
                () -> new ResourceNotFoundException("Item do checklist nao encontrado")
        );

        log.debug("deletando o item de checklist [guid={}]", guid);
        this.checklistItemRepository.delete(retriveItem
        );
    }
     public ChecklistItemEntity updateChecklistItem(String guid, String description, Boolean isCompleted, LocalDate deadline, String categoryGuid){
        if(!StringUtils.hasText(guid)){
            throw new IllegalArgumentException("Guid é nula");
        }
        ChecklistItemEntity retrivedChecklist = this.checklistItemRepository.findByGuid(guid).orElseThrow(
                ()-> new ResourceNotFoundException("Item do checklist não encontrado")
        );
         if (StringUtils.hasText(description)){
             retrivedChecklist.setDescription(description);
         }
         if (isCompleted != null){
             retrivedChecklist.setIsCompleted(isCompleted);
         }
         if (deadline != null){
             retrivedChecklist.setDeadline(deadline);
         }
         if (StringUtils.hasText(categoryGuid)){
             CategoryEntity retrivedCategory = this.categoryRepository.findByGuid(categoryGuid).orElseThrow(
                     ()-> new ResourceNotFoundException("Categoria não encontrada")
             );
             retrivedChecklist.setCategory(retrivedCategory);

         }

         log.debug("Checklist atualizado com sucesso [checklistItem]={}", retrivedChecklist.toString());
         return this.checklistItemRepository.save(retrivedChecklist);

     }
     public ChecklistItemEntity findByChecklistbyGuid(String guid){
        if(!StringUtils.hasText(guid)){
            throw new IllegalArgumentException("Guid nao pode ser nula ou vazia");
        }
        return this.checklistItemRepository.findByGuid(guid).orElseThrow(
                ()-> new ResourceNotFoundException("Nenhum item encontrado")
        );

     }

    public void updateIsCompleteStatus(String guid, boolean isCompleted) {
        if(!StringUtils.hasText(guid)){
            throw new IllegalArgumentException("Guid nao pode ser nula ou vazia");
        }
        ChecklistItemEntity resp = this.checklistItemRepository.findByGuid(guid).orElseThrow(
                ()-> new ResourceNotFoundException("Checklist Item não encontrado")
        );
        log.debug("update checklist item update status [guid={}]", guid);

        resp.setIsCompleted(isCompleted);
        log.debug(resp.getDescription(), resp.getIsCompleted());
        this.checklistItemRepository.save(resp);

    }
}
