package vn.velacorp.assignment.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import vn.velacorp.assignment.entity.OrderDetailEntity;
import vn.velacorp.assignment.entity.OrderEntity;
import vn.velacorp.assignment.enums.OrderStatus;
import vn.velacorp.assignment.model.ApiException;
import vn.velacorp.assignment.model.ERROR;
import vn.velacorp.assignment.model.dto.OrderDTO;
import vn.velacorp.assignment.model.dto.OrderResponseDTO;
import vn.velacorp.assignment.model.dto.OrderDetailDTO;
import vn.velacorp.assignment.model.dto.OrderProductDTO;
import vn.velacorp.assignment.model.request.OrderRequest;
import vn.velacorp.assignment.repository.OrderDetailRepository;
import vn.velacorp.assignment.repository.OrderRepository;
import vn.velacorp.assignment.service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOrder(OrderRequest request) throws ApiException {
        if (request == null)
            throw new ApiException(ERROR.INVALID_REQUEST, "Order request is null");
        if (CollectionUtils.isEmpty(request.getProducts()))
            throw new ApiException(ERROR.INVALID_REQUEST, "Order request has no product");
        if (!StringUtils.hasLength(request.getCustomerName()))
            throw new ApiException(ERROR.INVALID_REQUEST, "Customer name is required");
        if (!StringUtils.hasLength(request.getAddress()))
            throw new ApiException(ERROR.INVALID_REQUEST, "Customer address is required");
        if (!StringUtils.hasLength(request.getPhoneNumber()))
            throw new ApiException(ERROR.INVALID_REQUEST, "Customer phone number is required");

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCustomerName(request.getCustomerName());
        orderEntity.setAddress(request.getAddress());
        orderEntity.setEmail(request.getEmail());
        orderEntity.setPhoneNumber(request.getPhoneNumber());
        orderEntity.setStatus(OrderStatus.PENDING);
        orderEntity.setOrderDate(LocalDate.now());
        orderRepository.save(orderEntity);

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderDetailEntity> orderDetailEntities = new ArrayList<>();
        for (OrderProductDTO product : request.getProducts()) {
            OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
            orderDetailEntity.setProductId(product.getProductId());
            orderDetailEntity.setQuantity(product.getQuantity());
            orderDetailEntity.setUnitPrice(product.getUnitPrice());
            orderDetailEntity.setOrderId(orderEntity.getId());

            totalAmount = totalAmount.add(product.getUnitPrice().multiply(BigDecimal.valueOf(product.getQuantity())));
            orderDetailEntities.add(orderDetailEntity);
        }
        if (!CollectionUtils.isEmpty(orderDetailEntities))
            orderDetailRepository.saveAll(orderDetailEntities);

        orderEntity.setTotalAmount(totalAmount);
        orderRepository.save(orderEntity);
        return orderEntity.getId();
    }

    @Override
    public OrderDetailDTO getOrderDetail(Long orderId) throws ApiException {
        OrderEntity entity = getOrderEntity(orderId);
        List<OrderDetailEntity> orderDetails = orderDetailRepository.findByOrderId(orderId);
        OrderDetailDTO dto = new OrderDetailDTO();
        dto.setId(entity.getId());
        dto.setCustomerName(entity.getCustomerName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setAddress(entity.getAddress());
        dto.setEmail(entity.getEmail());
        dto.setOrderDate(entity.getOrderDate());
        dto.setStatus(entity.getStatus().name());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setProducts(orderDetails.stream().map(od -> OrderProductDTO
                        .builder()
                        .productId(od.getProductId())
                        .quantity(od.getQuantity())
                        .unitPrice(od.getUnitPrice())
                        .build()
                ).toList()
        );
        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long updateOrder(Long orderId, OrderRequest request) throws ApiException {
        OrderEntity orderEntity = getOrderEntity(orderId);
        if (request == null)
            throw new ApiException(ERROR.INVALID_REQUEST, "Order request is null");
        if (CollectionUtils.isEmpty(request.getProducts()))
            throw new ApiException(ERROR.INVALID_REQUEST, "Order request has no product");
        if (StringUtils.hasLength(request.getCustomerName()))
            orderEntity.setCustomerName(request.getCustomerName());
        if (StringUtils.hasLength(request.getAddress()))
            orderEntity.setAddress(request.getAddress());
        if (StringUtils.hasLength(request.getPhoneNumber()))
            orderEntity.setPhoneNumber(request.getPhoneNumber());
        if (StringUtils.hasLength(request.getEmail()))
            orderEntity.setEmail(request.getEmail());
        orderRepository.save(orderEntity);

        if (!CollectionUtils.isEmpty(request.getProducts())) {
            // Xoa het order detail cu
            orderDetailRepository.deleteByOrderId(orderId);

            BigDecimal totalAmount = BigDecimal.ZERO;
            List<OrderDetailEntity> orderDetailEntities = new ArrayList<>();
            for (OrderProductDTO product : request.getProducts()) {
                OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
                orderDetailEntity.setProductId(product.getProductId());
                orderDetailEntity.setQuantity(product.getQuantity());
                orderDetailEntity.setUnitPrice(product.getUnitPrice());
                orderDetailEntity.setOrderId(orderEntity.getId());

                totalAmount = totalAmount.add(product.getUnitPrice().multiply(BigDecimal.valueOf(product.getQuantity())));
                orderDetailEntities.add(orderDetailEntity);
            }
            if (!CollectionUtils.isEmpty(orderDetailEntities))
                orderDetailRepository.saveAll(orderDetailEntities);

            orderEntity.setTotalAmount(totalAmount);
            orderRepository.save(orderEntity);
        }
        return orderEntity.getId();
    }

    @Override
    public Page<OrderDTO> searchOrders(String customerName, Long orderId, int pageNumber, int pageSize) {
        return orderRepository.searchOrders(customerName, orderId, pageNumber, pageSize);
    }

    private OrderEntity getOrderEntity(Long id) throws ApiException {
        // Kiem tra xem order co ton tai trong DB khong
        Optional<OrderEntity> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty())
            throw new ApiException(ERROR.DATA_NOT_FOUND, "Order not found");
        return optionalOrder.get();
    }

    private OrderResponseDTO convertToOrderDTO(OrderEntity entity) {
        if (entity == null) return null;
        return OrderResponseDTO.builder()
                .id(entity.getId())
                .customerName(entity.getCustomerName())
                .phoneNumber(entity.getPhoneNumber())
                .address(entity.getAddress())
                .email(entity.getEmail())
                .orderDate(entity.getOrderDate())
                .status(entity.getStatus().name())
                .totalAmount(entity.getTotalAmount())
                .build();
    }
}
