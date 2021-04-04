package com.apbdoo.BooksStore.dto;

import com.apbdoo.BooksStore.models.Book;
import com.apbdoo.BooksStore.models.BookCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class BookCategoryDTO {

    private int id;

    private String bookCategoryDescription;

    private List<Book> books;

    public BookCategoryDTO() {
    }

    public BookCategoryDTO(int id, String bookCategoryDescription) {
        this.id = id;
        this.bookCategoryDescription = bookCategoryDescription;
    }

    public BookCategoryDTO(BookCategory bookCategory) {
        this.id = bookCategory.getId();
        this.bookCategoryDescription = bookCategory.getBookCategoryDescription();
    }
}
