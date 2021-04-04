package com.apbdoo.BooksStore.dto;

import com.apbdoo.BooksStore.models.Author;
import com.apbdoo.BooksStore.models.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    private int id;

    private String firstName;

    private String lastName;

    private List<Book> books;

    public AuthorDTO (Author author){
        this.id = author.getId();
        this.firstName = author.getFirstName();
        this.lastName = author.getLastName();
        this.books = author.getBooks();
    }
}
