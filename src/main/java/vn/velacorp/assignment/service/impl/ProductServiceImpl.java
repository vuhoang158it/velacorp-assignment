package vn.velacorp.assignment.service.impl;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
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
import static vn.velacorp.assignment.model.ERROR.INVALID_REQUEST;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createProduct(CreateProductRequest request) throws ApiException {
        if (request == null)
            throw new ApiException(INVALID_REQUEST, "Request is null");
        if (!StringUtils.hasLength(request.getName())) {
            throw new ApiException(INVALID_REQUEST, "Name is required");
        }
        if (request.getPrice() == null || request.getPrice().doubleValue() <= 0) {
            throw new ApiException(INVALID_REQUEST, "Price must be greater than zero");
        }
        if (request.getStockQuantity() == null || request.getStockQuantity() < 0) {
            throw new ApiException(INVALID_REQUEST, "Stock quantity must be zero or greater");
        }
        ProductEntity product = new ProductEntity();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product = productRepository.save(product);
        return product.getId();
    }

    @Override
    @Cacheable(value = "products", key = "#id")
    public ProductDTO getProductDetail(Long id) throws ApiException {
        ProductEntity product = getProductEntity(id);
        return convertToProductDTO(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CachePut(value = "${spring.cache.names.products}", key = "#id")
    public Long updateProduct(Long id, UpdateProductRequest request) throws ApiException {
        ProductEntity product = getProductEntity(id);
        if (request == null)
            throw new ApiException(INVALID_REQUEST, "Request is null");
        if (StringUtils.hasLength(request.getDescription())) {
            product.setDescription(request.getDescription());
        }
        if (request.getPrice() != null) {
            if (request.getPrice().doubleValue() <= 0)
                throw new ApiException(INVALID_REQUEST, "Price must be greater than zero");
            product.setPrice(request.getPrice());
        }
        productRepository.save(product);
        return product.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProduct(Long id) throws ApiException {
        productRepository.delete(getProductEntity(id));
    }

    @Override
    @Cacheable(value = "products", key = "'allProducts'")
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(this::convertToProductDTO).toList();
    }

    @Override
    public Page<ProductDTO> searchProducts(String name, String description, int pageNumber, int pageSize) {
        return productRepository.searchProducts(name, description, pageNumber, pageSize);
    }

    private ProductEntity getProductEntity(Long id) throws ApiException {
        // Kiem tra xem product co ton tai trong DB khong
        Optional<ProductEntity> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty())
            throw new ApiException(DATA_NOT_FOUND, "Product not found");
        return optionalProduct.get();
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
