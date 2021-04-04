package com.apbdoo.BooksStore.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorControllerTestIntegration {

    @Autowired
    AuthorController authorController;

    @MockBean
    private BindingResult bindingResult;

    @Before
    public void setUp() throws Exception {
    }

  /*  @Test
    @WithMockUser
    public void getAuthorList() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
        mockMvc.perform(get("/author/authorList"))
                .andExpect(view().name("authorlist"));
    }*/

    @Test
    public void listBooksPageByPage() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
        mockMvc.perform(get("/author/authorList/page/1").param("page","1"))
                .andExpect(status().isOk())
                .andExpect(view().name("authorlist"));

    }

    @Test
    @WithMockUser(username="ADMIN",roles="ADMIN")
    public void addAuthor()  throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
        mockMvc.perform(get("/author/authorList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addAuthor"));

    }

    @Test
    @WithMockUser(username="ADMIN",roles="ADMIN")
    public void saveAuthor()  throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
      //  BindingResult result = Mockito.mock(BindingResult.class);
       // Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        mockMvc.perform(post("/author/save/test"))
                .andExpect(view().name("redirect:/author/authorList/page/1"));

    }

    @Test
    @WithMockUser(username="ADMIN",roles="ADMIN")
    public void editById() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
        mockMvc.perform(get("/edit/10").param("id","10"))
                .andExpect(status().isNotFound());

    }


    @Test
    @WithMockUser(username="ADMIN",roles="ADMIN")
    public void editByValidId() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
        mockMvc.perform(get("/author/edit/3").param("id","3"))
                .andExpect(status().isOk())
                .andExpect(view().name("editAuthor"));
    }


    @Test
    @WithMockUser(username="ADMIN",roles="ADMIN")
    public void deleteById() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
        mockMvc.perform(get("/author/delete/3").param("id","3"))
                .andExpect(view().name("redirect:/author/authorList/page/1"));
    }

    @Test
    @WithMockUser(username="ADMIN",roles="ADMIN")
    public void updateById() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
        mockMvc.perform(get("/author/update/3/test").param("id","3"))
                .andExpect(view().name("redirect:/author/authorList/page/1"));
    }
}
