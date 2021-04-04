package com.apbdoo.BooksStore.services;

import com.apbdoo.BooksStore.dto.BookInfoDTO;
import com.apbdoo.BooksStore.dto.ResultDTO;
import com.apbdoo.BooksStore.models.*;
import com.apbdoo.BooksStore.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class BookInfoService {

    private BookInfoRepository bookInfoRepository;
    private BookRepository bookRepository;

    @Autowired
    public BookInfoService(BookInfoRepository bookInfoRepository) {
        this.bookInfoRepository = bookInfoRepository;
    }

    public void seedBooksInfo() {
        seedBookInfo("Maitreyi",  "1245785428757", LocalDate.of(1933, 8, 13));
        seedBookInfo("Ana Karenina", "5236987412569",LocalDate.of(1877,10,03));
        seedBookInfo("The Martian", "1456987321456",LocalDate.of(2011,04,22));
        seedBookInfo("Shakespeare's sonnets", "15478963547824",LocalDate.of(1609,07, 11));
        seedBookInfo("Multiple Authors Book", "15478963547825",LocalDate.of(2019,07, 11));
    }

    private void seedBookInfo(String title, String bookISBN, LocalDate date) {
        BookInfo bookInfo = bookInfoRepository.findByBookTitle(title);
        if (bookInfo == null) {
            bookInfo = new BookInfo()
                    .setBookTitle(title)
                    .setBookISBN(bookISBN)
                    .setBookPublicationDate(date);
            bookInfoRepository.save(bookInfo);
        }
    }

    public BookInfoDTO getBookInfoById (int Id){
        BookInfo bookInfo = bookInfoRepository.findById(Id);
        return new BookInfoDTO(bookInfo);
    }

    public List<BookInfo> getAll() {
        return bookInfoRepository.findAll();
    }

    public BookInfoDTO createBookInfo(BookInfoDTO bookInfo) {
        BookInfo newBookInfo = new BookInfo()
                .setBookISBN(bookInfo.getBookISBN())
                .setBookPublicationDate(bookInfo.getBookPublicationDate())
                .setBookTitle(bookInfo.getBookTitle());
        return new BookInfoDTO(bookInfoRepository.save(newBookInfo));
    }

    public BookInfoDTO editBookInfo(int id, BookInfoDTO bookInfo) {
        BookInfo updatedBookInfo = bookInfoRepository.findById(id);
        updatedBookInfo.setBookTitle(bookInfo.getBookTitle())
                .setBookPublicationDate(bookInfo.getBookPublicationDate())
                .setBookISBN(bookInfo.getBookISBN());
        bookInfoRepository.save(updatedBookInfo);
        return new BookInfoDTO(updatedBookInfo);
    }

    public ResultDTO deleteBookInfo(int id) {
        BookInfo deleteBookInfo = bookInfoRepository.findById(id);
        bookInfoRepository.delete(deleteBookInfo);
        Book deleteBook = bookRepository.findById(id);
        bookRepository.delete(deleteBook);
        return new ResultDTO().setType("success").setMessage("Book deleted.");
    }
}