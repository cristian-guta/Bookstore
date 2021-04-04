package com.apbdoo.BooksStore.services;


import com.apbdoo.BooksStore.dto.BookDTO;
import com.apbdoo.BooksStore.dto.ResultDTO;
import com.apbdoo.BooksStore.models.*;
import com.apbdoo.BooksStore.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



@Service
public class BookService {

    private BookRepository bookRepository;
    private BookCategoryRepository bookCategoryRepository;
    private BookInfoRepository bookInfoRepository;
    private AuthorRepository authorRepository;
    private OrderRepository orderRepository;
    private ShoppingCartRepository shoppingCartRepository;


    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Autowired
    public BookService(
            BookRepository bookRepository,
            BookCategoryRepository bookCategoryRepository,
            BookInfoRepository bookInfoRepository,
            AuthorRepository authorRepository,
            OrderRepository orderRepository,
            ShoppingCartRepository shoppingCartRepository
    ) {
        this.bookRepository = bookRepository;
        this.bookCategoryRepository = bookCategoryRepository;
        this.bookInfoRepository = bookInfoRepository;
        this.authorRepository = authorRepository;
        this.orderRepository = orderRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public void seedBooks() {

        seedBook(
                35.55,
                bookInfoRepository.findByBookTitle("Maitreyi"),
                bookCategoryRepository.findByBookCategoryDescription("Standard"),
                (List<Author>) authorRepository.findAllById(Collections.singleton(1))
        );


        seedBook(
                15.65,
                bookInfoRepository.findByBookTitle("Ana Karenina"),
                bookCategoryRepository.findByBookCategoryDescription("Realistic"),
                (List<Author>) authorRepository.findAllById(Collections.singleton(3))
        );

        seedBook(
                10.22,
                bookInfoRepository.findByBookTitle("The Martian"),
                bookCategoryRepository.findByBookCategoryDescription("Fiction"),
                (List<Author>) authorRepository.findAllById(Collections.singleton(2))
        );

        seedBook(
                50.69,
                bookInfoRepository.findByBookTitle("Shakespeare's sonnets"),
                bookCategoryRepository.findByBookCategoryDescription("Poems"),
                (List<Author>) authorRepository.findAllById(Collections.singleton(4))
        );


        ArrayList<Integer> bookIds = new ArrayList<Integer>();
        bookIds.add(1);
        bookIds.add(4);
        seedBook(
                100.99,
                bookInfoRepository.findByBookTitle("Multiple Authors Book"),
                bookCategoryRepository.findByBookCategoryDescription("Fiction"),
                (List<Author>) authorRepository.findAllById(bookIds)
        );

    }

    private void seedBook(Double price, BookInfo bookInfo, BookCategory bookCategory, List<Author> authors) {
        Book book = bookRepository.findByPriceAndBookInfoAndBookCategory(
            price, bookInfo, bookCategory
        );
        if (book == null) {
            Book newBook = new Book()
                .setPrice(price)
                .setBookInfo(bookInfo)
                .setBookCategory(bookCategory)
                .setAuthors(authors);
            bookRepository.save(newBook);
        }
    }

    public BookDTO getBook (String title){
        BookInfo bookInfo = bookInfoRepository.findByBookTitle(title);
        Book book = bookRepository.findByBookInfo(bookInfo);
        return new BookDTO(book);
    }

    public List<BookDTO> getAllBooksByCategory (BookCategory bookCategory){
        List<Book> listOfBooks = bookRepository.findByBookCategory(bookCategory);
        List<BookDTO> listOfAllBooks = new ArrayList<>();
        for(int i = 0; i < listOfBooks.size(); i++)
            listOfAllBooks.add(new BookDTO(listOfBooks.get(i)));
        return listOfAllBooks;
    }


    public List<BookDTO> getBooksByAuthor (Author author){
        List<Book> listOfBooks = bookRepository.findByAuthors(author);
        List<BookDTO> listOfAllBooks = new ArrayList<>();
        for(int i = 0; i < listOfBooks.size(); i++)
            listOfAllBooks.add(new BookDTO(listOfBooks.get(i)));
        return listOfAllBooks;
    }


    public BookDTO createBook(BookDTO book) {
        Book newBook = new Book()
                .setPrice(book.getPrice())
                .setBookInfo(book.getBookInfo())
                .setBookCategory(book.getBookCategory());
        return new BookDTO(bookRepository.save(newBook));
    }


    public BookDTO editBook(int id, BookDTO book) {
        Book updatedBook = bookRepository.findById(id);
        updatedBook
                .setPrice(book.getPrice())
                .setBookInfo(book.getBookInfo())
                .setBookCategory(book.getBookCategory());
        bookRepository.save(updatedBook);
        return new BookDTO(updatedBook);
    }

    public ResultDTO deleteBook(int id) {
        Book book = bookRepository.findById(id);
        bookRepository.delete(book);
        return new ResultDTO().setType("success").setMessage("Book deleted.");
    }

}
