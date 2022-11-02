package org.springframework.samples.petclinic.product;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class Test1 {

    @Autowired(required = false)
    ProductRepository pr;

    @Test
    public void test1(){
        repositoryExists();
        repositoryContainsMethod();
        testConstraints();
    }

    
    public void repositoryExists(){
        assertNotNull(pr,"The repository was not injected into the tests, its autowired value was null");
    }

    
    public void repositoryContainsMethod(){
        if(pr!=null){
            Product p=pr.findByName("This product does not exist");
            assertNull(p,"No result (null) should be returned for a product name that does not exist");
        }else
            fail("The repository was not injected into the tests, its autowired value was null");
    }

     void testConstraints(){
        Product p=new Product();
        p.setId(7);
        p.setName("Kitty bed");
        p.setPrice(-10.50);
        assertThrows(ConstraintViolationException.class,() -> pr.save(p),
        "You are not constraining "+
        "product price to positive values");
        p.setPrice(10.0);
        p.setName("ja");
        assertThrows(ConstraintViolationException.class,() -> pr.save(p),
        "You are not constraining name size to more than 3 characters"
        );
        p.setName("En un lugar de la mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero, adarga antigua, rocín flaco y galgo corredor.");
        assertThrows(ConstraintViolationException.class,() -> pr.save(p),
        "you are not constraining name size to less than 51 characters");
    }

}
