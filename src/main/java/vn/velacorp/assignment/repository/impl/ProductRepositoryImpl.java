package vn.velacorp.assignment.repository.impl;

import org.springframework.data.domain.Page;
import vn.velacorp.assignment.model.dto.ProductDTO;
import vn.velacorp.assignment.repository.ProductRepositoryCustom;

public class ProductRepositoryImpl implements ProductRepositoryCustom {
    @Override
    public Page<ProductDTO> searchProducts(String name, String description, int page, int size) {
        return null;
    }
}
