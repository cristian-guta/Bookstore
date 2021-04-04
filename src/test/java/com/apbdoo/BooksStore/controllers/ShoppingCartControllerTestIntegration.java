package com.apbdoo.BooksStore.controllers;


import com.apbdoo.BooksStore.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;

import java.security.Principal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShoppingCartControllerTestIntegration {

    @Autowired
    ShoppingCartController shoppingCartController;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void listShoppingPageByPage() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(shoppingCartController).build();
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("biancaa2609");
        mockMvc.perform(get("/shoppingCart/shoppingCartList/page/1").param("page","1").principal(mockPrincipal))
                .andExpect(status().isOk())
                .andExpect(view().name("viewShoppingCart"));

    }

    @Test
    public void addToShoppingCart()  throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(shoppingCartController).build();
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("biancaa2609");
        mockMvc.perform(get("/shoppingCart/add/2").param("id","2").principal(mockPrincipal))
                .andExpect(view().name("redirect:/book/bookList/page/1"));

    }

    @Test
    public void deleteFromShoppingCart() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(shoppingCartController).build();
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("biancaa2609");
        mockMvc.perform(get("/shoppingCart/delete/4").param("id","4").principal(mockPrincipal))
                .andExpect(view().name("redirect:/shoppingCart/add/viewCart?"));
    }
}
