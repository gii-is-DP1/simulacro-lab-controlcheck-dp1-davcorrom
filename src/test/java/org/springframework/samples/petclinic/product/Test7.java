package org.springframework.samples.petclinic.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class Test7 {
    @Autowired
    ProductRepository pr;
    

    @Test
    @DisplayName("The DB is initialized with two products associated to two product types")
    public void testInitialProducts(){
        
        Optional<Product> p1=pr.findById(1);
        assertTrue(p1.isPresent(),"Product with id:1 not found");
        assertNotNull(p1.get().getProductType(),"The product with id:1 has not a product type associated");
        assertEquals("Accessories",p1.get().getProductType().getName(),"The name of the product type associated to the product with id:1 is not 'Accessories'");

        Optional<Product> p2=pr.findById(2);
        assertTrue(p2.isPresent(),"Product with id:2 not found");
        assertNotNull(p2.get().getProductType(),"The product with id:2 has not a product type associated");
        assertEquals(p2.get().getProductType().getName(),"Food","The name of the product type associated to the product with id:2 is not 'Food'");

    }
}
