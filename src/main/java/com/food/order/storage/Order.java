package com.food.order.storage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "order")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    private String id;
    private String email;
    private List<CartItem> items;
    private double itemTotal;
    private double bill;
    private Address address;
    private Date orderDate;
    private Date deliverDate;
    private boolean ordered;
    private boolean delivered;
}
