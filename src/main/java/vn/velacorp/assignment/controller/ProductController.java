package vn.velacorp.assignment.controller;

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
    public BaseResponse<Long> createProduct(@RequestBody CreateProductRequest request) {
        BaseResponse<Long> response = new PagingResponse<>();
        response.setData(productService.createProduct(request));
        return response;
    }

    @GetMapping("/{id}")
    public BaseResponse<ProductDTO> getProductDetail(@PathVariable Long id) throws ApiException {
        BaseResponse<ProductDTO> response = new BaseResponse<>();
        response.setData(productService.getProductDetail(id));
        return response;
    }

    @PatchMapping("/{id}")
    public BaseResponse<Long> updateProduct(@PathVariable Long id, @RequestBody UpdateProductRequest request) {
        BaseResponse<Long> response = new BaseResponse<>();
        response.setData(productService.updateProduct(id, request));
        return response;
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Void> deleteProduct(@PathVariable Long id) throws ApiException {
        productService.deleteProduct(id);
        return new BaseResponse<>();
    }

    @GetMapping
    public BaseResponse<List<ProductDTO>> getAllProducts() {
        BaseResponse<List<ProductDTO>> response = new BaseResponse<>();
        response.setData(productService.getAllProducts());
        return response;
    }

    @GetMapping("/search")
    public PagingResponse<List<ProductDTO>> searchProducts(@RequestParam(required = false) @Schema(description = "Tên sản phẩm", example = "iPhone 12") String name,
                                                           @RequestParam(required = false) @Schema(description = "Mô tả sản phẩm", example = "Điện thoại Iphone 12") String description,
                                                           @RequestParam(defaultValue = "1") @Schema(description = "Trang hiện tại", example = "1") int page,
                                                           @RequestParam(defaultValue = "10") @Schema(description = "Số lượng sản phẩm trên trang", example = "10") int size) {
        PagingResponse<List<ProductDTO>> response = new PagingResponse<>();
        Page<ProductDTO> productDTOPage = productService.searchProducts(name, description, page, size);
        response.setCurrentPage(productDTOPage.getNumber());
        response.setTotalElement(productDTOPage.getTotalElements());
        response.setSize(productDTOPage.getSize());
        response.setData(productDTOPage.getContent());
        return response;
    }
}
