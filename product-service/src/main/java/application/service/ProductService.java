package application.service;

import application.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO getProductById(Long id);

    List<ProductDTO> getAllProducts();

    List<ProductDTO> getProductsByCategory(Long categoryId);

    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    ProductDTO partialUpdateProduct(Long id, ProductDTO productDTO);

    void deleteProduct(Long id);
}
