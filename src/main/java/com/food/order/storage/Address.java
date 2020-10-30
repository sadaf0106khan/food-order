package com.food.order.storage;

import com.food.order.enums.AddressType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "address")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Address {
    @Id
    private String id;
    private String email;
    private AddressType addressType;
    private String flatNumber;
    private String landmark;
    private String city;
    private int pin;
    private boolean selected;
}
