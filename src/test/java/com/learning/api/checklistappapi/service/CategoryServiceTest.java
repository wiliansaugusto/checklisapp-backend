package com.learning.api.checklistappapi.service;

import com.learning.api.checklistappapi.entity.CategoryEntity;
import com.learning.api.checklistappapi.repository.CategoryRepository;
import com.learning.api.checklistappapi.repository.ChecklistItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    private CategoryService categoryService;

    @Mock
    private ChecklistItemRepository checklistItemRepository;
    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void initTest() {
        this.categoryService = new CategoryService(checklistItemRepository, categoryRepository);
    }

    @Test
    public void shouldACreateACategorySuccessfully() {
        //having
        String name = "Personal";
        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(new CategoryEntity());
        //when
        CategoryEntity categoryEntity = this.categoryService.addNewCatergory(name);


        //then
        Assertions.assertNotNull(categoryEntity);
        verify(categoryRepository, times(1)).save(
                argThat(categoryEntityArg -> categoryEntityArg.getName().equals(name)
                        && categoryEntityArg.getGuid() != null)

        );

    }

    @Test
    public void shouldThroenAnExecpetionWhenCategoryNameIsNullOrEmpty() {
        //having
        String name = null;
        //when
        Exception exception =
                Assertions.assertThrows(IllegalArgumentException.class, () -> this.categoryService.addNewCatergory(name));
        //then
        assertThat(exception.getMessage(), is("Nome da categoria nao pode ser nula"));
    }

    @Test
    public void shouldAUpdatACategorySuccessfully(){
        //having
        String guid = UUID.randomUUID().toString();
        String name = "Other";

        CategoryEntity savedCategory = new CategoryEntity();
        savedCategory.setGuid(guid);
        savedCategory.setName("Personal");

        when(categoryRepository.findByGuid(guid)).thenReturn(Optional.of(savedCategory));
        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(new CategoryEntity());
        //when
        CategoryEntity categoryEntity =
                this.categoryService.updateCategory(guid, name);

        //then
        Assertions.assertNotNull(categoryEntity);
        verify(categoryRepository,times(1)).save(
                argThat(categoryEntity1 -> categoryEntity1.getName().equals(name)
                        && categoryEntity1.getGuid().equals(guid))
        );

    }
}
