package vn.velacorp.assignment.service;

import org.springframework.data.domain.Page;
import vn.velacorp.assignment.model.ApiException;
import vn.velacorp.assignment.model.dto.OrderDTO;
import vn.velacorp.assignment.model.dto.OrderDetailDTO;
import vn.velacorp.assignment.model.request.OrderRequest;

public interface OrderService {
    Long createOrder(OrderRequest request) throws ApiException;
    OrderDetailDTO getOrderDetail(Long orderId) throws ApiException;
    Long updateOrder(Long orderId, OrderRequest request) throws ApiException;
    Page<OrderDTO> searchOrders(String customerName, Long orderId, int pageNumber, int pageSize);
}
