package com.apbdoo.BooksStore.services;

import com.apbdoo.BooksStore.dto.AuthorDTO;
import com.apbdoo.BooksStore.models.Author;
import com.apbdoo.BooksStore.repositories.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Set;

@SpringBootTest
@Transactional
@Slf4j
public class AuthorServiceTests {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void getAllAuthors(){
       Set<Author> authors = authorService.getAllAuthors();
       log.info("getAll...");
       authors.forEach(author -> log.info(author.getLastName()));
    }

    @Test
    public void createAuthor(){
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(7).setFirstName("Noah").setLastName("Salloway");
        AuthorDTO newAuthor = authorService.createAuthor(authorDTO);
        Set<Author> authors = authorService.getAllAuthors();
        log.info("getAll...");
        authors.forEach(author -> log.info(author.getLastName()));

    }

    @Test
    public void editAuthor() {
        Author author = new Author();
        author.setId(3).setFirstName("Camilllllll").setLastName("Petrescu");
        authorService.editAuthor(3,author);
        Set<Author> authors = authorService.getAllAuthors();
        log.info("getAll...");
        authors.forEach(authornew -> log.info(authornew.getFirstName()));

    }

    @Test
    public void deleteAuthor(){
        authorService.deleteAuthor(2);
        Set<Author> authors = authorService.getAllAuthors();
        log.info("getAll...");
        authors.forEach(authornew -> log.info(authornew.getFirstName()));
    }

    @Test
    public void numberOfAuthors(){
        int nbr = authorService.numberOfAuthorsBook("Multiple Authors Book");
        System.out.print("Numarul autorilor acestei carti este " + nbr + "\n\n");

    }
}
