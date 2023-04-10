package com.truyum.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OrderDetails {
    int id;
    String name;
    int price;
    boolean active;
    String dateOfLaunch;
    String category;
    String freeDelivery;

}
