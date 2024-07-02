package vn.velacorp.assignment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import vn.velacorp.assignment.model.ApiException;
import vn.velacorp.assignment.model.dto.OrderDTO;
import vn.velacorp.assignment.model.dto.OrderResponseDTO;
import vn.velacorp.assignment.model.request.OrderRequest;
import vn.velacorp.assignment.model.response.BaseResponse;
import vn.velacorp.assignment.model.response.PagingResponse;
import vn.velacorp.assignment.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/v1/orders")
@Log4j2
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @Operation(summary = "Thêm mới đơn hàng",
            description = "Tạo đơn hàng mới với thông tin cá nhân và danh sách sản phẩm để hỗ trợ khách hàng đặt hàng.",
            tags = {"Quản lý Đơn hàng"}
    )
    public BaseResponse<Long> createOrder(@RequestBody OrderRequest request) throws ApiException {
        BaseResponse<Long> response = new BaseResponse<>();
        response.setData(orderService.createOrder(request));
        return response;
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Xem thông tin chi tiết của một đơn hàng",
            description = "Xem thông tin chi tiết của một đơn hàng dựa trên ID để kiểm tra tình trạng đơn hàng.",
            tags = {"Quản lý Đơn hàng"}
    )
    public BaseResponse<OrderResponseDTO> getOrderDetail(@PathVariable Long orderId) throws ApiException {
        BaseResponse<OrderResponseDTO> response = new BaseResponse<>();
        response.setData(orderService.getOrderDetail(orderId));
        return response;
    }

    @PatchMapping("/{orderId}")
    @Operation(summary = "Cập nhật thông tin của một đơn hàng",
            description = "Cập nhật thông tin của một đơn hàng để phục vụ yêu cầu vận hành và yêu cầu của khách hàng.",
            tags = {"Quản lý Đơn hàng"}
    )
    public BaseResponse<Long> updateOrder(@PathVariable Long orderId, @RequestBody OrderRequest request) throws ApiException {
        BaseResponse<Long> response = new BaseResponse<>();
        response.setData(orderService.updateOrder(orderId, request));
        return response;
    }

    @GetMapping
    @Operation(summary = "Tìm kiếm đơn hàng",
            description = "Tìm kiếm đơn hàng dựa trên tên khách hàng hoặc ID đơn hàng để dễ dàng tìm kiếm đơn hàng cụ thể.",
            tags = {"Quản lý Đơn hàng"}
    )
    public PagingResponse<List<OrderDTO>> searchOrders(@RequestParam(required = false) String customerName,
                                                       @RequestParam(required = false) Long orderId,
                                                       @RequestParam(defaultValue = "1") @Schema(description = "Trang hiện tại", example = "1") int pageNumber,
                                                       @RequestParam(defaultValue = "10") @Schema(description = "Số lượng sản phẩm trên trang", example = "10") int pageSize) {
        PagingResponse<List<OrderDTO>> response = new PagingResponse<>();
        Page<OrderDTO> orderDTOPage = orderService.searchOrders(customerName, orderId, pageNumber, pageSize);
        response.setCurrentPage(orderDTOPage.getNumber() + 1);
        response.setTotalElement(orderDTOPage.getTotalElements());
        response.setPageSize(orderDTOPage.getSize());
        response.setData(orderDTOPage.getContent());
        return response;
    }
}
