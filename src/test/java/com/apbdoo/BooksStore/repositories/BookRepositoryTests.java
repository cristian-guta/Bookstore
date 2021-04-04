package com.apbdoo.BooksStore.repositories;

import com.apbdoo.BooksStore.models.Author;
import com.apbdoo.BooksStore.models.Book;
import com.apbdoo.BooksStore.models.BookCategory;
import com.apbdoo.BooksStore.models.BookInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
public class BookRepositoryTests {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void findByAuthor() {
        Author author = new Author();
        List<Book> bookList = new ArrayList<>();
        bookList.add(bookRepository.findById(1));
        author.setId(1).setFirstName("Mircea").setLastName("Eliade").setBooks(bookList);
        List<Book> books = bookRepository.findByAuthors(author);
        log.info("findByAuthors...");
        books.forEach(book -> log.info(book.getBookInfo().getBookTitle()));

    }

    @Test
    public void findByBookCategory() {
        BookCategory bookCategory = new BookCategory();
        bookCategory.setId(1).setBookCategoryDescription("Standard");
        List<Book> books = bookRepository.findByBookCategory(bookCategory);
        log.info("findByCategory...");
        books.forEach(book -> log.info(book.getBookInfo().getBookTitle()));

    }

    @Test
    public void findByBookInfo() {
        BookInfo bookInfo = new BookInfo();
        bookInfo.setId(3).setBookISBN("1456987321456").setBookTitle("The Martian").setBookPublicationDate(LocalDate.of(2011,04,22));
        Book book = bookRepository.findByBookInfo(bookInfo);
        System.out.print(book);

    }

    @Test
    public void findAllPage() {
        PageRequest pageable = PageRequest.of(0, 2, Sort.by("id"));
        Page<Book> page = bookRepository.findAllPage(pageable);
        log.info("findByPage...");
        page.forEach(pg -> log.info(pg.getBookInfo().getBookTitle()));

    }



}
