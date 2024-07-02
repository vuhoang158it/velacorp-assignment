package vn.velacorp.assignment.service;

import org.springframework.data.domain.Page;
import vn.velacorp.assignment.model.ApiException;
import vn.velacorp.assignment.model.dto.ProductDTO;
import vn.velacorp.assignment.model.request.CreateProductRequest;
import vn.velacorp.assignment.model.request.UpdateProductRequest;

import java.util.List;

public interface ProductService {

    Long createProduct(CreateProductRequest request) throws ApiException;

    ProductDTO getProductDetail(Long id) throws ApiException;

    Long updateProduct(Long id, UpdateProductRequest request) throws ApiException;

    void deleteProduct(Long id) throws ApiException;

    Page<ProductDTO> searchProducts(String name, String description, int pageNumber, int pageSize);
}
