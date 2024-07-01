package vn.velacorp.assignment.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vn.velacorp.assignment.entity.ProductEntity;
import vn.velacorp.assignment.model.ApiException;
import vn.velacorp.assignment.model.dto.ProductDTO;
import vn.velacorp.assignment.model.request.CreateProductRequest;
import vn.velacorp.assignment.model.request.UpdateProductRequest;
import vn.velacorp.assignment.repository.ProductRepository;
import vn.velacorp.assignment.service.ProductService;

import java.util.List;
import java.util.Optional;

import static vn.velacorp.assignment.model.ERROR.DATA_NOT_FOUND;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Long createProduct(CreateProductRequest request) {
        return 0L;
    }

    @Override
    public ProductDTO getProductDetail(Long id) throws ApiException {
        Optional<ProductEntity> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty())
            throw new ApiException(DATA_NOT_FOUND, "Product not found");
        ProductEntity product = optionalProduct.get();
        return convertToProductDTO(product);
    }

    @Override
    public Long updateProduct(Long id, UpdateProductRequest request) {
        return 0L;
    }

    @Override
    public void deleteProduct(Long id) throws ApiException {
        Optional<ProductEntity> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty())
            throw new ApiException(DATA_NOT_FOUND, "Product not found");
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(this::convertToProductDTO).toList();
    }

    @Override
    public Page<ProductDTO> searchProducts(String name, String description, int page, int size) {
        return productRepository.searchProducts(name, description, page, size);
    }

    private ProductDTO convertToProductDTO(ProductEntity entity) {
        if (entity == null) return null;
        return ProductDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .stockQuantity(entity.getStockQuantity())
                .build();
    }
}
