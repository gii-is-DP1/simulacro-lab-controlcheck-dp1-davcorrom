package org.springframework.samples.petclinic.product;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ProductController.class,
		includeFilters = @ComponentScan.Filter(value = ProductTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
public class Test10 {
    @MockBean
	private ProductService productService;

    @Autowired
	private MockMvc mockMvc;

    @BeforeEach
    public void configureMock(){
        ProductType pt=new ProductType();
        pt.setName("Food");
        Mockito.when(productService.save(any(Product.class))).thenReturn(null);
        Mockito.when(productService.getProductType("Food")).thenReturn(pt);
    }

    @WithMockUser(value = "spring", authorities = {"admin"})
    @Test
    void test10()  throws Exception {
        testProductCreationControllerOK();
        testProductCreationControllerWrongPrice();
        testProductCreationControllerWrongProductName();
    }

	void testProductCreationControllerOK() throws Exception {
        mockMvc.perform(post("/product/create")
                            .with(csrf())
                            .param("name", "cookies")
                            .param("productType", "Food")
                            .param("price", "2.5"))
                .andExpect(status().isOk())
				.andExpect(view().name("welcome"));
    }

    
	void testProductCreationControllerWrongPrice() throws Exception {
        mockMvc.perform(post("/product/create")
                            .with(csrf())
                            .param("name", "cookies")
                            .param("productType", "Food")
                            .param("price", "-2.5"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("product"))
				.andExpect(view().name("products/createOrUpdateProductForm"));
    }

    
	void testProductCreationControllerWrongProductName() throws Exception {
        mockMvc.perform(post("/product/create")
                            .with(csrf())
                            .param("name", "")
                            .param("productType", "Food")
                            .param("price", "2.5"))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("product"))
				.andExpect(view().name("products/createOrUpdateProductForm"));
    }
}
