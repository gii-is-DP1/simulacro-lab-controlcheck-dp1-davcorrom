package org.springframework.samples.petclinic.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class Test5 {
    @Autowired
    ProductService ps;

    @Test
    public void test5(){
        validateFindProductTypeByName();
        validateNotFoundProductTypeByName();
    }



    public void validateFindProductTypeByName(){
        String typeName="Food";
        ProductType pt=ps.getProductType(typeName);
        assertNotNull(pt);
        assertEquals(typeName,pt.getName());
    }

    
    public void validateNotFoundProductTypeByName(){
        String typeName="This is not a valid product type name";
        ProductType pt=ps.getProductType(typeName);
        assertNull(pt);
    }

}
