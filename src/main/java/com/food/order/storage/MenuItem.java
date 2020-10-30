package com.food.order.storage;

import com.food.order.enums.MenuType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "menu_item")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem {
    @Id
    private String id;
    private String title;
    private MenuType menuType;
    private String description;
    private String imageUrl;
    private double price;
}
