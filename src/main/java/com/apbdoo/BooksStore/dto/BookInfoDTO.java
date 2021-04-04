package com.apbdoo.BooksStore.dto;

import com.apbdoo.BooksStore.models.BookInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BookInfoDTO {
    private int id;

    private String bookISBN;

    private String bookTitle;

    private LocalDate bookPublicationDate;

    public BookInfoDTO(BookInfo bookInfo){
        this.id = bookInfo.getId();
        this.bookISBN = bookInfo.getBookISBN();
        this.bookTitle = bookInfo.getBookTitle();
        this.bookPublicationDate = bookInfo.getBookPublicationDate();
    }
}
