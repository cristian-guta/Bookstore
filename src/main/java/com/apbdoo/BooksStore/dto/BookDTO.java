package com.apbdoo.BooksStore.dto;

import com.apbdoo.BooksStore.models.Author;
import com.apbdoo.BooksStore.models.Book;
import com.apbdoo.BooksStore.models.BookCategory;
import com.apbdoo.BooksStore.models.BookInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class BookDTO {

    private  int id;

    private Double price;

    private BookInfo bookInfo;

    private BookCategory bookCategory;

    private List<Author> authors;

    public BookDTO(Book book){
        this.id = book.getId();
        this.price = book.getPrice();
        this.bookInfo = book.getBookInfo();
        this.bookCategory = book.getBookCategory();
    }
}
