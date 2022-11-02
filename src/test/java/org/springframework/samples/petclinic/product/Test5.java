package org.springframework.samples.petclinic.product;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class Test5 {

    @Autowired(required = false)
    ProductService ps;

    @Test public void test4(){

        productServiceIsInjected();
        productServiceCanGetProducts();
    }


    

    
    public void productServiceIsInjected()
    {
        assertNotNull(ps,"ProductService was not injected by spring");       
    }

    
    public void productServiceCanGetProducts(){
        assertNotNull(ps,"ProductService was not injected by spring");
        List<Product> products=ps.getAllProducts();
        assertNotNull(products,"The list of products found by the ProductService was null");
        assertFalse(products.isEmpty(),"The list of products found by the ProductService was empty");       
    }
}
