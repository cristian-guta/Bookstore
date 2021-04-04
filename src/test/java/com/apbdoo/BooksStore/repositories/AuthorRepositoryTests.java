package com.apbdoo.BooksStore.repositories;

import com.apbdoo.BooksStore.models.Author;
import com.apbdoo.BooksStore.models.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
public class AuthorRepositoryTests {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void findByName(){
        Author author = authorRepository.findByFirstNameAndLastName("Mihai","Eminescu");
        System.out.print("Autor: " + author);
    }

    @Test
    public void findByBooks(){
        Book book = bookRepository.findById(5);
        List<Author> authors = authorRepository.findByBooks(book);
        log.info("findByBooks...");
        authors.forEach(author -> log.info(author.getLastName()));
    }

    @Test
    public void findAllPage() {
        PageRequest pageable = PageRequest.of(0, 2, Sort.by("id"));
        Page<Author> page = authorRepository.findAllPage(pageable);
        log.info("findByPage...");
        page.forEach(pg -> log.info(pg.getLastName()));

    }
}
