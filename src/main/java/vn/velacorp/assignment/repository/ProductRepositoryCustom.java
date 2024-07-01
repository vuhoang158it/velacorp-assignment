package vn.velacorp.assignment.repository;

import org.springframework.data.domain.Page;
import vn.velacorp.assignment.model.dto.ProductDTO;

public interface ProductRepositoryCustom {
    Page<ProductDTO> searchProducts(String name, String description, int page, int size);
}
