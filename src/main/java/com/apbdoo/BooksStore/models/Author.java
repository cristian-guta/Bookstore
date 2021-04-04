package com.apbdoo.BooksStore.models;

import com.apbdoo.BooksStore.repositories.BookRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Accessors(chain = true)
@Table(name="author")
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;

    private String lastName;


    @ManyToMany(mappedBy = "authors")
    private List<Book> books;

    @Override
    public String toString() {
        return "BookCategory: [id = " + id + ", firstName = " + firstName +
                ", lastName = " + lastName + "]";
    }

    @PreRemove
    public void deleteAuthorFromBooks() {
        for (Book book : books) {
            List<Author> authors = book.getAuthors();
            authors.remove(this);
        }
    }
    public Author (int id, String firstName, String lastName){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}