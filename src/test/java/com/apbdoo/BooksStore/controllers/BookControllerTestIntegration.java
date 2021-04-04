package com.apbdoo.BooksStore.controllers;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookControllerTestIntegration {

    @Autowired
    BookController bookController;

    @Before
    public void setUp() throws Exception {
    }

 /*   @Test
    @WithMockUser
    public void getBookList() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        mockMvc.perform(get("/book/bookList"))
                .andExpect(view().name("booklist"));
    }*/

    @Test
    public void listBooksPageByPage() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        mockMvc.perform(get("/book/bookList/page/1").param("page","1"))
                .andExpect(status().isOk())
                .andExpect(view().name("booklist"));

    }

    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    public void addBook()  throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        mockMvc.perform(get("/book/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addBook"));

    }

    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    public void saveBook()  throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        mockMvc.perform(get("/book/save"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));

    }

    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    public void editById() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        mockMvc.perform(get("/edit/12").param("id","12"))
                .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    public void editByValidId() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        mockMvc.perform(get("/book/edit/2").param("id","2"))
                .andExpect(status().isOk())
         .andExpect(view().name("editBook"));
    }
    @Test
    @WithMockUser(username="admin",roles="ADMIN")
    public void updateById() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        mockMvc.perform(get("/book/update/2").param("id","2"))
                .andExpect(view().name("redirect:/book/bookList/page/1"));
    }

    @Test
    @WithMockUser(username="ADMIN",roles="ADMIN")
    public void deleteById() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        mockMvc.perform(get("/book/delete/2").param("id","2"))
                .andExpect(view().name("redirect:/book/bookList/page/1"));
    }

}