package org.springframework.samples.petclinic.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class Test3 {
    @Autowired
    ProductRepository pr;
    
    @Test
    public void test3(){
        testInitialProducts();
        testInitialProductTypes();
    }
    
    public void testInitialProducts(){
        List<Product> products=pr.findAll();
        assertTrue(products.size()==2, "Exactly two products should be present in the DB");

        Optional<Product> p1=pr.findById(1);
        assertTrue(p1.isPresent(),"There should exist a product with id:1");
        assertEquals(p1.get().getName(),"Wonderful dog collar");
        assertEquals(p1.get().getPrice(),17.25,"The price of the product with id:1 should be 17.25 €");

        Optional<Product> p2=pr.findById(2);
        assertTrue(p2.isPresent(),"There should exist a product with id:2");
        assertEquals(p2.get().getName(),"Super Kitty Cookies","The name of the product with id:2 should be 'Super Kitty Cookies'");
        assertEquals(p2.get().getPrice(),50.0,"The price of the product with id:2 should be 50.0 €");

    }

    public void testInitialProductTypes()
    {
        List<ProductType> productTypes=pr.findAllProductTypes();
        assertTrue(productTypes.size()==2,"Exactly two product types should be present in the DB");

        for(ProductType pt:productTypes)
        {
            if(pt.getId()==1){
                assertEquals(pt.getName(),"Accessories","The name of the product type with id:1 should be 'Accesories'");
            }else if(pt.getId()==2){
                assertEquals(pt.getName(),"Food","The name of the product type with id:2 should be 'Food'");
            }else
                fail("The id of the product types should be either 1 or 2");
        }
    }
}
