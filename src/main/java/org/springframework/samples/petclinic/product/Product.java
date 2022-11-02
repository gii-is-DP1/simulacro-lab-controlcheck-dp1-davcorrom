package org.springframework.samples.petclinic.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    Integer id;
    String name;
    double price;
    ProductType productType;
}
