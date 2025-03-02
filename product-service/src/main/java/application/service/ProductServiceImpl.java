package application.service;

import application.dto.ProductDTO;
import application.entity.Category;
import application.entity.Product;
import application.exception.CategoryNotFoundException;
import application.exception.ErrorMessages;
import application.exception.ProductNotFoundException;
import application.repository.CategoryRepository;
import application.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        validateProductDTO(productDTO);
        Product product = modelMapper.map(productDTO, Product.class);
        return saveProduct(productDTO, product);
    }

    @Override
    public ProductDTO getProductById(Long id) {
        validateId(id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
    }

    @Override
    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        if (categoryId == null) {
            throw new IllegalArgumentException(ErrorMessages.CATEGORY_ID_NULL.getMessage());
        }

        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException(categoryId);
        }

        return productRepository.findByCategoryId(categoryId).stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        validateIdAndProductDTO(id, productDTO);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        updateProductFields(product, productDTO, true);

        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO partialUpdateProduct(Long id, ProductDTO productDTO) {
        validateIdAndProductDTO(id, productDTO);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        updateProductFields(product, productDTO, false);

        Product updatedProduct = productRepository.save(product);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    @Override
    public void deleteProduct(Long id) {
        validateId(id);
        productRepository.deleteById(id);
    }

    private void updateProductFields(Product product, ProductDTO productDTO, boolean isFullUpdate) {
        if (isFullUpdate || productDTO.getName() != null) {
            product.setName(productDTO.getName());
        }
        if (isFullUpdate || productDTO.getDescription() != null) {
            product.setDescription(productDTO.getDescription());
        }
        if (isFullUpdate || productDTO.getPrice() != null) {
            product.setPrice(productDTO.getPrice());
        }
        if (isFullUpdate || productDTO.getCategoryId() != null) {
            if (productDTO.getCategoryId() != null) {
                Category category = categoryRepository.findById(productDTO.getCategoryId())
                        .orElseThrow(() -> new CategoryNotFoundException(productDTO.getCategoryId()));
                product.setCategory(category);
            } else {
                product.setCategory(null);
            }
        }
    }

    private void validateIdAndProductDTO(Long id, ProductDTO productDTO) {
        validateId(id);
        validateProductDTO(productDTO);
    }

    private void validateId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException(ErrorMessages.ID_NULL.getMessage());
        }
    }

    private ProductDTO saveProduct(ProductDTO productDTO, Product product) {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(productDTO.getCategoryId()));
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    private void validateProductDTO(ProductDTO productDTO) {
        if (productDTO == null) {
            throw new IllegalArgumentException(ErrorMessages.PRODUCT_DTO_NULL.getMessage());
        }
        if (productDTO.getCategoryId() == null) {
            throw new IllegalArgumentException(ErrorMessages.CATEGORY_ID_NULL.getMessage());
        }
    }
}
