package vn.velacorp.assignment.model.dto;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO extends OrderResponseDTO {
    private List<OrderProductDTO> products;
}
