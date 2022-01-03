package com.learning.api.checklistappapi.service;


import com.learning.api.checklistappapi.entity.CategoryEntity;
import com.learning.api.checklistappapi.entity.ChecklistItemEntity;
import com.learning.api.checklistappapi.exception.CategoryServiceException;
import com.learning.api.checklistappapi.exception.ResourceNotFoundException;
import com.learning.api.checklistappapi.repository.CategoryRepository;
import com.learning.api.checklistappapi.repository.ChecklistItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class CategoryService {
    private ChecklistItemRepository checklistItemRepository;
    private CategoryRepository categoryRepository;


    public CategoryService(ChecklistItemRepository checklistItemRepository, CategoryRepository categoryRepository) {
        this.checklistItemRepository = checklistItemRepository;
        this.categoryRepository = categoryRepository;
    }

    public CategoryEntity addNewCatergory(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Nome da categoria nao pode ser nula");
        }
        CategoryEntity newCategory = new CategoryEntity();
        newCategory.setGuid(UUID.randomUUID().toString());
        newCategory.setName(name);
        log.debug("Adding a new category with the [name = {}]", name);
        return this.categoryRepository.save(newCategory);
    }

    public CategoryEntity updateCategory(String guid, String name) {
        System.out.println(guid);
        System.out.println(name);
        if (guid == null || !StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Dados de atualização invalidos");
        }
        CategoryEntity retriveCategory = this.categoryRepository.findByGuid(guid).orElseThrow(
                () -> new ResourceNotFoundException("Categoria não encontrada")
        );

        retriveCategory.setName(name);
        log.debug("Gategoria Atualizada com sucesso [guid ={}, newName ={}]", guid, name);
        return this.categoryRepository.save(retriveCategory);
    }

        public void deleteCategory(String guid) {
        if (!StringUtils.hasText(guid)) {
            throw new IllegalArgumentException("A categoria informada é nula ou vazia");
        }
        CategoryEntity retriveCategory = this.categoryRepository.findByGuid(guid).orElseThrow(
                () -> new ResourceNotFoundException("Categoria não encontrada")
        );
        List<ChecklistItemEntity> checklistItems = this.checklistItemRepository.findByCategoryName(guid);
        if (!CollectionUtils.isEmpty(checklistItems)){
            throw new CategoryServiceException("Não é possivel deletar a categoria, a mesma esta sendo usada por items do checklist");
        }
log.debug("Deleting Category [guid={}]", guid);
        this.categoryRepository.delete(retriveCategory);
    }
     public CategoryEntity findCategory(String guid){
         if (!StringUtils.hasText(guid)) {
             throw new IllegalArgumentException("A categoria informada é nula ou vazia");
         }
         return this.categoryRepository.findByGuid(guid).orElseThrow(
                 () -> new ResourceNotFoundException("Categoria não encontrada")
         );
     }

     public Iterable<CategoryEntity> findAllCategories(){
        return this.categoryRepository.findAll();
     }

}