package application.service;

import application.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO getCategoryById(Long id);

    List<CategoryDTO> getAllCategories();

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    CategoryDTO partialUpdateCategory(Long id, CategoryDTO categoryDTO);

    void deleteCategory(Long id);
}
