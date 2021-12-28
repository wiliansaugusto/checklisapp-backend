package com.learning.api.checklistappapi.dataloader;

import com.learning.api.checklistappapi.entity.CategoryEntity;
import com.learning.api.checklistappapi.repository.CategoryRepository;
import com.learning.api.checklistappapi.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class DBLoader implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;

    @Override
    public void run(String... args) throws Exception {
        log.info("Populando o banca de dados de categorias");
        List<String> categoryNames = Arrays.asList(
                "Trabalho", "Saúde", "Educação", "Pessoal", "Outros","Família"
        );

        for (String categoryName : categoryNames) {
            Optional<CategoryEntity> catOpt = this.categoryRepository.findByName(categoryName);
            if (!catOpt.isPresent()) {
                categoryService.addNewCatergory(categoryName);
            }
        }
    }

}
