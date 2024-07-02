package vn.velacorp.assignment.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import vn.velacorp.assignment.model.dto.OrderDTO;
import vn.velacorp.assignment.repository.OrderRepositoryCustom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderRepositoryImpl implements OrderRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<OrderDTO> searchOrders(String customerName, Long orderId, int pageNumber, int pageSize) {
        StringBuilder jpql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        jpql.append("select * from assignment.orders where true ");
        if (StringUtils.hasLength(customerName)) {
            jpql.append("and customer_name ilike :customerName ");
            params.put("customerName", "%" + customerName + "%");
        }
        if (orderId != null) {
            jpql.append("and id = :orderId ");
            params.put("orderId", orderId);
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

        Query q = entityManager.createNativeQuery(jpql.toString(), OrderDTO.class);
        params.forEach(q::setParameter);

        return new PageImpl<>(q.getResultList(), pageable, total);
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
