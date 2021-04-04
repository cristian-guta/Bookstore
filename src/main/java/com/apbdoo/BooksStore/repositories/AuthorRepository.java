package com.apbdoo.BooksStore.repositories;

import com.apbdoo.BooksStore.models.Author;

import com.apbdoo.BooksStore.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, Integer> {

    Author findById(int id);
    Author findByFirstNameAndLastName(String firstName, String lastName);
    List<Author> findByBooks (Book book);
    @Query("select c from Author c")
    Page<Author> findAllPage(Pageable pageable);

}

