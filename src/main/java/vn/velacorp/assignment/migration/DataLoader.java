package vn.velacorp.assignment.migration;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import vn.velacorp.assignment.entity.OrderDetailEntity;
import vn.velacorp.assignment.entity.OrderEntity;
import vn.velacorp.assignment.entity.ProductEntity;
import vn.velacorp.assignment.enums.OrderStatus;
import vn.velacorp.assignment.repository.OrderDetailRepository;
import vn.velacorp.assignment.repository.OrderRepository;
import vn.velacorp.assignment.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Profile("dev")
@Log4j2
public class DataLoader implements CommandLineRunner {

    private static final Random RANDOM = new Random();

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;

    public DataLoader(ProductRepository productRepository, OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {

        if (productRepository.count() == 0) {
            log.info("No data found. Start loading data...");
            // Tạo 100 sản phẩm mẫu
            List<ProductEntity> products = new ArrayList<>();
            for (int i = 1; i <= 100; i++) {
                ProductEntity product = new ProductEntity();
                product.setName("Product " + i);
                product.setDescription("Description for product " + i);
                product.setPrice(BigDecimal.valueOf(10L + RANDOM.nextInt(1000)));
                product.setStockQuantity(RANDOM.nextInt(100));
                products.add(product);
            }
            productRepository.saveAll(products);


            // Tạo 100 đơn hàng mẫu
            List<OrderEntity> orders = new ArrayList<>();
            for (int i = 1; i <= 100; i++) {
                OrderEntity order = new OrderEntity();
                order.setOrderDate(LocalDate.now().minusDays(RANDOM.nextInt(30)));
                order.setCustomerName("Customer " + i);
                order.setAddress("Address " + i);
                order.setEmail("customer" + i + "@example.com");
                order.setPhoneNumber("123456789" + i);
                order.setStatus(OrderStatus.values()[RANDOM.nextInt(OrderStatus.values().length)]);
                order.setTotalAmount(BigDecimal.valueOf(0.0));
                orders.add(order);
            }
            orderRepository.saveAll(orders);

            // Tạo chi tiết đơn hàng mẫu
            List<OrderDetailEntity> orderDetails = new ArrayList<>();
            for (OrderEntity order : orders) {
                int numberOfProducts = 1 + RANDOM.nextInt(5);
                for (int j = 0; j < numberOfProducts; j++) {
                    OrderDetailEntity orderDetail = new OrderDetailEntity();
                    orderDetail.setOrderId(order.getId());
                    ProductEntity product = products.get(RANDOM.nextInt(products.size()));
                    orderDetail.setProductId(product.getId());
                    orderDetail.setQuantity(1 + RANDOM.nextInt(10));
                    orderDetail.setUnitPrice(product.getPrice());
                    orderDetails.add(orderDetail);
                    // Cập nhật tổng tiền đơn hàng
                    order.setTotalAmount(order.getTotalAmount().add(orderDetail.getUnitPrice().multiply(BigDecimal.valueOf(orderDetail.getQuantity()))));
                }
            }
            orderDetailRepository.saveAll(orderDetails);
            orderRepository.saveAll(orders); // Cập nhật lại tổng tiền đơn hàng
            log.info("Data loaded successfully.");
        }
    }
}
