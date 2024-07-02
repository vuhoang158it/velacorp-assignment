package vn.velacorp.assignment.repository;

import org.springframework.data.domain.Page;
import vn.velacorp.assignment.model.dto.OrderDTO;
import vn.velacorp.assignment.model.dto.OrderResponseDTO;

public interface OrderRepositoryCustom {
    Page<OrderDTO> searchOrders(String customerName, Long orderId, int pageNumber, int pageSize);
}
