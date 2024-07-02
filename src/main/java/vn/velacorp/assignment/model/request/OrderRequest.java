package vn.velacorp.assignment.model.request;

import lombok.Getter;
import lombok.Setter;
import vn.velacorp.assignment.model.dto.OrderProductDTO;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private String customerName;
    private String address;
    private String email;
    private String phoneNumber;
    private List<OrderProductDTO> products;
}
