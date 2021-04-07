package com.apbdoo.BooksStore.models;


import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Data
@Entity
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
@Getter
@Setter
@Audited
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NumberFormat
    private Double price;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, cascade = {ALL})
    @JoinColumn(name = "book_info_id")
    @NotAudited
    private BookInfo bookInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookcategory_id")
    @NotAudited
    private BookCategory bookCategory;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "authors_books",
            joinColumns = {
                    @JoinColumn(name = "book_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "author_id")
            }
    )
    @NotAudited
    private List<Author> authors;

}
