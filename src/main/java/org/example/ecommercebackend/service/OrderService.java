package org.example.ecommercebackend.service;

import org.example.ecommercebackend.dto.OrderItemRequest;
import org.example.ecommercebackend.dto.OrderRequest;
import org.example.ecommercebackend.entity.Order;
import org.example.ecommercebackend.entity.OrderItem;
import org.example.ecommercebackend.entity.OrderStatus;
import org.example.ecommercebackend.entity.Product;
import org.example.ecommercebackend.repository.OrderItemRepository;
import org.example.ecommercebackend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductService productService;

    public Order createOrder(OrderRequest orderRequest) {
        Order order = new Order(
                orderRequest.getCustomerEmail(),
                orderRequest.getCustomerName(),
                orderRequest.getTotalAmount()
        );

        Order savedOrder = orderRepository.save(order);

        // Create order items
        for (OrderItemRequest itemRequest : orderRequest.getItems()) {
            Optional<Product> productOpt = productService.getProductById(itemRequest.getProductId());
            if (productOpt.isPresent()) {
                Product product = productOpt.get();

                // Check stock
                if (product.getStock() < itemRequest.getQuantity()) {
                    throw new RuntimeException("Insufficient stock for product: " + product.getName());
                }

                OrderItem orderItem = new OrderItem(
                        savedOrder,
                        product,
                        itemRequest.getQuantity(),
                        itemRequest.getPrice()
                );

                orderItemRepository.save(orderItem);

                // Update product stock
                productService.updateStock(product.getId(),
                        product.getStock() - itemRequest.getQuantity());
            }
        }

        return savedOrder;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersByCustomerEmail(String email) {
        return orderRepository.findByCustomerEmailOrderByCreatedAtDesc(email);
    }

    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            order.setStatus(status);
            return orderRepository.save(order);
        }
        throw new RuntimeException("Order not found");
    }
}
