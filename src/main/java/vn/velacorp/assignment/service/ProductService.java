package vn.velacorp.assignment.service;

import org.springframework.data.domain.Page;
import vn.velacorp.assignment.model.ApiException;
import vn.velacorp.assignment.model.dto.ProductDTO;
import vn.velacorp.assignment.model.request.CreateProductRequest;
import vn.velacorp.assignment.model.request.UpdateProductRequest;

import java.util.List;

public interface ProductService {

    Long createProduct(CreateProductRequest request);

    ProductDTO getProductDetail(Long id) throws ApiException;

    Long updateProduct(Long id, UpdateProductRequest request);

    void deleteProduct(Long id) throws ApiException;

    List<ProductDTO> getAllProducts();

    Page<ProductDTO> searchProducts(String name, String description, int page, int size);
}
