package org.springframework.samples.petclinic.product;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class Test8 {
    @Autowired
    ProductService ps;

    @Test
    public void testProductsCheaperThan(){
        int expectedProducts[]={2,1,0};
        double prices[]={100.0,30.0,10.0};
        double price; 
        int expectedNumberOfProducts;
        List<Product> products=null;
        for(int i=0;i<prices.length;i++){
            expectedNumberOfProducts=expectedProducts[i];
            price=prices[i];
            products=ps.getProductsCheaperThan(price);
            assertEquals(expectedNumberOfProducts,products.size(),
                "The number of products obtained for a price of "+price+
                " was "+products.size()+" but I expected it to be "+expectedNumberOfProducts);
    
        }

    }
}
