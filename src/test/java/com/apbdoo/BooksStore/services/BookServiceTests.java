package com.apbdoo.BooksStore.services;

import com.apbdoo.BooksStore.dto.BookDTO;
import com.apbdoo.BooksStore.models.Author;
import com.apbdoo.BooksStore.models.Book;
import com.apbdoo.BooksStore.models.BookCategory;
import com.apbdoo.BooksStore.models.BookInfo;
import com.apbdoo.BooksStore.repositories.BookInfoRepository;
import com.apbdoo.BooksStore.repositories.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Slf4j
public class BookServiceTests {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookInfoRepository bookInfoRepository;

    @Test
    public void getBook() {
        BookDTO bookDTO = bookService.getBook("The Martian");
        assertEquals("The Martian", bookDTO.getBookInfo().getBookTitle());
    }

    @Test
    public void getAllBooksByCategory() {
        BookCategory bookCategory = new BookCategory();
        bookCategory.setId(3).setBookCategoryDescription("Fiction");
        List<BookDTO> books = bookService.getAllBooksByCategory(bookCategory);
        assertEquals(2, books.size());
    }

    @Test
    public void editBook() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setPrice(88.8);
        BookDTO editedBook = bookService.editBook(1, bookDTO);
        assertEquals(88.8, editedBook.getPrice());
    }

//    @Test
//    public void deleteBook() {
//        bookService.deleteBook(1);
//        List<Book> books = bookRepository.findAll();
//        log.info("Books from repository ");
//        books.forEach(book -> log.info(book.getBookInfo().getBookTitle()));
//    }

    @Test
    public void createBook() {
        BookDTO bookDTO = new BookDTO();
        BookCategory bookCategory = new BookCategory();
        bookCategory.setId(1).setBookCategoryDescription("Standard");
        bookDTO.setId(6).setPrice(17.45).setBookCategory(bookCategory);
        BookInfo bookInfo = new BookInfo();
        bookInfo.setId(6).setBookTitle("Ultima noapte de Dragoste. Intaia noapte de razboi").setBookISBN("123456");


        BookInfo bookInfo1 = bookInfoRepository.save(bookInfo);


        List<Author> authors = new ArrayList<>(1);
        Author author = new Author();
        author.setId(3).setFirstName("Camil").setLastName("Petrescu");
        authors.add(author);
        bookDTO.setBookInfo(bookInfo);
        bookDTO.setAuthors(authors);

//        List<Book> books = bookRepository.findAll();
//        log.info("Books from repository ");
//        books.forEach(book -> log.info(book.getBookCategory().getBookCategoryDescription()));
    }

    @Test
    public void getBooksByAuthor() {
        Author author = new Author();
        author.setId(3).setFirstName("Camil").setLastName("Petrescu");
        List<Book> books = bookRepository.findByAuthors(author);
        assertEquals(2, books.size());
        log.info("Books from repository ");
        books.forEach(book -> log.info(book.getBookInfo().getBookTitle()));

    }
}
