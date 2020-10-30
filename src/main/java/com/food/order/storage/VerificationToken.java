package com.food.order.storage;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "verification_token")
@Data
@Builder
public class VerificationToken {
    @Id
    private String id;
    private String token;
    private User user;
    private Date expiryTime;
}
