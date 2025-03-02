package application.service;

import application.dto.CategoryDTO;
import application.entity.Category;
import application.exception.CategoryNotFoundException;
import application.exception.ErrorMessages;
import application.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        validateCategoryDTO(categoryDTO);
        Category category = modelMapper.map(categoryDTO, Category.class);
        return saveCategory(category);
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        validateId(id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        validateIdAndCategoryDTO(id, categoryDTO);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        updateCategoryFields(category, categoryDTO, true);

        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO partialUpdateCategory(Long id, CategoryDTO categoryDTO) {
        validateIdAndCategoryDTO(id, categoryDTO);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        updateCategoryFields(category, categoryDTO, false);

        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }

    private void updateCategoryFields(Category category, CategoryDTO categoryDTO, boolean isFullUpdate) {
        if (isFullUpdate || categoryDTO.getName() != null) {
            category.setName(categoryDTO.getName());
        }
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    private void validateCategoryDTO(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            throw new IllegalArgumentException(ErrorMessages.CATEGORY_DTO_NULL.getMessage());
        }
    }

    private void validateId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException(ErrorMessages.ID_NULL.getMessage());
        }
    }

    private void validateIdAndCategoryDTO(Long id, CategoryDTO categoryDTO) {
        validateId(id);
        validateCategoryDTO(categoryDTO);
    }

    private CategoryDTO saveCategory(Category category) {
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }
}
