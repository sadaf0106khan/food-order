package com.food.order.storage;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@Builder
@Data
public class User {
    @Id
    private String userId;
    private String name;
    private String email;
    private String password;
    private String roles;
    private boolean enabled;
}
