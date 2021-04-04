package com.apbdoo.BooksStore.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Accessors(chain = true)
@AllArgsConstructor
@Table(name = "bookcategory")
public class BookCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String bookCategoryDescription;

    @OneToMany(mappedBy = "bookCategory")
    private List<Book> books;

    public BookCategory() {
    }

    public BookCategory(@NotNull String bookCategoryDescription) {
        this.bookCategoryDescription = bookCategoryDescription;
    }

    @Override
    public String toString() {
        return "BookCategory: [id = " + id + ", bookCategoryDescription = " +
                bookCategoryDescription + "]";
    }
}
