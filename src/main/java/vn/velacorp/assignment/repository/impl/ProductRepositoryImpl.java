package vn.velacorp.assignment.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import vn.velacorp.assignment.model.dto.ProductDTO;
import vn.velacorp.assignment.repository.ProductRepositoryCustom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<ProductDTO> searchProducts(String name, String description, int pageNumber, int pageSize) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        jpql.append("select * from assignment.products where true ");
        if (StringUtils.hasLength(name)) {
            jpql.append("and name ilike :name ");
            params.put("name", "%" + name + "%");
        }
        if (StringUtils.hasLength(description)) {
            jpql.append("and description ilike :description ");
            params.put("description", "%" + description + "%");
        }

        long total = countQuery(jpql, params);

        Query selectQuery = entityManager.createNativeQuery(jpql.toString());

        if (!params.isEmpty()) {
            params.forEach(selectQuery::setParameter);
        }

        pageNumber = pageNumber > 0 ? pageNumber - 1 : 0;
        int limit = pageSize > 0 ? pageSize : 10;
        int offset = (Math.max(pageNumber, 0)) * limit;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        if (total < 1) {
            return new PageImpl<>(new ArrayList<>(), pageable, total);
        }

        jpql.append(" order by id desc ");
        jpql.append(" limit :limit offset :offset ");

        params.put("limit", pageSize);
        params.put("offset", offset);

        Query q = entityManager.createNativeQuery(jpql.toString(), ProductDTO.class);
        params.forEach(q::setParameter);

        return new PageImpl<ProductDTO>(q.getResultList(), pageable, total);
    }

    protected long countQuery(StringBuilder sql, Map<String, Object> params) {
        Query query = entityManager.createNativeQuery("SELECT COUNT(*) FROM (" + sql + " ) COUNT");

        if (!params.isEmpty()) {
            params.forEach(query::setParameter);
        }

        Object result = query.getSingleResult();

        return ((Number) result).longValue();
    }
}
