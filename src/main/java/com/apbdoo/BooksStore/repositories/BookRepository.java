package com.apbdoo.BooksStore.repositories;


import com.apbdoo.BooksStore.models.Author;
import com.apbdoo.BooksStore.models.Book;
import com.apbdoo.BooksStore.models.BookCategory;
import com.apbdoo.BooksStore.models.BookInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    //List<Book> findAll();
    List<Book> findByAuthors(Author author);
    Book findByBookInfo(BookInfo bookInfo);
    List<Book> findByBookCategory(BookCategory bookCategory);
    Book findById(int id);
    Book findByPriceAndBookInfoAndBookCategory(
        Double price, BookInfo bookInfo, BookCategory bookCategory
    );

    @Query("select c from Book c")
    Page<Book> findAllPage(Pageable pageable);
}
