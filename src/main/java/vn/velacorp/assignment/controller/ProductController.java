package vn.velacorp.assignment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import vn.velacorp.assignment.model.ApiException;
import vn.velacorp.assignment.model.dto.ProductDTO;
import vn.velacorp.assignment.model.request.CreateProductRequest;
import vn.velacorp.assignment.model.request.UpdateProductRequest;
import vn.velacorp.assignment.model.response.BaseResponse;
import vn.velacorp.assignment.model.response.PagingResponse;
import vn.velacorp.assignment.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/v1/products")
@Log4j2
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @Operation(summary = "Thêm mới sản phẩm",
            description = "Thêm một sản phẩm mới vào cửa hàng để cập nhật danh sách sản phẩm.",
            tags = {"Quản lý Sản phẩm"}
    )
    public BaseResponse<Long> createProduct(@RequestBody CreateProductRequest request) throws ApiException {
        BaseResponse<Long> response = new PagingResponse<>();
        response.setData(productService.createProduct(request));
        return response;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Xem thông tin chi tiết của một sản phẩm",
            description = "Xem thông tin chi tiết của một sản phẩm dựa trên ID để kiểm tra các thuộc tính của sản phẩm.",
            tags = {"Quản lý Sản phẩm"}
    )
    public BaseResponse<ProductDTO> getProductDetail(@PathVariable Long id) throws ApiException {
        BaseResponse<ProductDTO> response = new BaseResponse<>();
        response.setData(productService.getProductDetail(id));
        return response;
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Cập nhật thông tin của một sản phẩm",
            description = "Cập nhật thông tin của một sản phẩm để điều chỉnh giá cả hoặc mô tả sản phẩm.",
            tags = {"Quản lý Sản phẩm"}
    )
    public BaseResponse<Long> updateProduct(@PathVariable Long id, @RequestBody UpdateProductRequest request) throws ApiException {
        BaseResponse<Long> response = new BaseResponse<>();
        response.setData(productService.updateProduct(id, request));
        return response;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Xóa một sản phẩm",
            description = "Xóa một sản phẩm dựa trên ID để loại bỏ các sản phẩm không còn cần thiết.",
            tags = {"Quản lý Sản phẩm"}
    )
    public BaseResponse<Void> deleteProduct(@PathVariable Long id) throws ApiException {
        productService.deleteProduct(id);
        return new BaseResponse<>();
    }

    @GetMapping
    @Operation(summary = "Lấy danh sách tất cả các sản phẩm",
            description = "Lấy danh sách tất cả các sản phẩm để xem toàn bộ danh sách sản phẩm có sẵn trong cửa hàng.",
            tags = {"Quản lý Sản phẩm"}
    )
    public BaseResponse<List<ProductDTO>> getAllProducts() {
        BaseResponse<List<ProductDTO>> response = new BaseResponse<>();
        response.setData(productService.getAllProducts());
        return response;
    }

    @GetMapping("/search")
    @Operation(summary = "Tìm kiếm sản phẩm",
            description = "Tìm kiếm sản phẩm dựa trên tên hoặc mô tả để dễ dàng tìm kiếm sản phẩm cụ thể.",
            tags = {"Quản lý Sản phẩm"}
    )
    public PagingResponse<List<ProductDTO>> searchProducts(@RequestParam(required = false) @Schema(description = "Tên sản phẩm", example = "iPhone 12") String name,
                                                           @RequestParam(required = false) @Schema(description = "Mô tả sản phẩm", example = "Điện thoại Iphone 12") String description,
                                                           @RequestParam(defaultValue = "1") @Schema(description = "Trang hiện tại", example = "1") int pageNumber,
                                                           @RequestParam(defaultValue = "10") @Schema(description = "Số lượng sản phẩm trên trang", example = "10") int pageSize) {
        PagingResponse<List<ProductDTO>> response = new PagingResponse<>();
        Page<ProductDTO> productDTOPage = productService.searchProducts(name, description, pageNumber, pageSize);
        response.setCurrentPage(productDTOPage.getNumber() + 1);
        response.setTotalElement(productDTOPage.getTotalElements());
        response.setPageSize(productDTOPage.getSize());
        response.setData(productDTOPage.getContent());
        return response;
    }
}
