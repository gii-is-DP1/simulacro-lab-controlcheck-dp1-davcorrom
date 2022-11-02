package org.springframework.samples.petclinic.product;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class Test2 {
    @Autowired(required = false)
    ProductRepository pr;
    @Autowired(required = false)
    EntityManager em;
    
    @Test
    public void test2(){
        entityExists();
        repositoryContainsMethod();
        testConstraints();
    }

   
    public void entityExists()
    {
        ProductType p=new ProductType();
        p.setId(7);
        p.setName("Toys");        
    }  

    
    public void repositoryContainsMethod(){
        if(pr!=null){
            List<ProductType> pts=pr.findAllProductTypes();
            assertNotNull(pts,"We can not query all the products through the repository");
        }else
            fail("The repository was not injected into the tests, its autowired value was null");
    }

    
    public void testConstraints(){
        
        ProductType pt=new ProductType();       
        pt.setName("ja");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        assertFalse(validator.validate(pt).isEmpty());
        pt.setName("En un lugar de la mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero, adarga antigua, rocín flaco y galgo corredor.");
        assertFalse(validator.validate(pt).isEmpty());
        pt.setName("prueba");
        em.persist(pt);
        
        ProductType pt2=new ProductType();       
        pt2.setName("prueba");
        assertThrows(PersistenceException.class,()->em.persist(pt2));
        
    }
}
