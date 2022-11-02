package org.springframework.samples.petclinic.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class Test6 {

    @Autowired
    ProductTypeFormatter formatter;

    @Test
    public void test6(){
        testFormatterIsInjected();
        testFormatterObject2String();
        testFormatterString2Object();
        testFormatterString2ObjectNotFound();
    }


    
    public void testFormatterIsInjected(){
        assertNotNull(formatter);
    }

    
    public void testFormatterObject2String(){
        ProductType pt=new ProductType();
        pt.setName("Prueba");
        String result=formatter.print(pt, null);
        assertEquals("Prueba",result);
    }

    
    public void testFormatterString2Object(){
        String name="Food";
        ProductType pt;
        try {
            pt = formatter.parse(name, null);
            assertNotNull(pt);
            assertEquals(pt.getName(),name);
        } catch (ParseException e) {           
            e.printStackTrace();
            fail(e.getMessage());
        }
        
    }

    
    public void testFormatterString2ObjectNotFound(){
        String name="This is not a product type";
        assertThrows(ParseException.class, () -> formatter.parse(name, null));          
    }
}
