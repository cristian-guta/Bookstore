package com.apbdoo.BooksStore.repositories;

import com.apbdoo.BooksStore.models.BookInfo;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookInfoRepository extends CrudRepository<BookInfo, Integer> {
    BookInfo findById(int id);
    BookInfo findByBookTitle(String title);
    BookInfo findByBookPublicationDateAfter(LocalDate date);
    BookInfo findByBookISBN(String bookISBN);
    List<BookInfo> findAll();
}
