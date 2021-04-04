package com.apbdoo.BooksStore.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Member;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Data
@Entity
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name="book")
@ToString(exclude = {"orders", "shoppingCarts"})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NumberFormat
    private Double price;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval =true, cascade={ALL})
    @JoinColumn(name="book_info_id")
    private BookInfo bookInfo;

    @ManyToOne(fetch = FetchType.LAZY/*, cascade = CascadeType.ALL*/)
    @JoinColumn(name = "bookcategory_id")
    private BookCategory bookCategory;

    @ManyToMany(fetch = FetchType.LAZY/*, cascade = CascadeType.ALL*/)
    @JoinTable(
        name = "authors_books",
        joinColumns = {
            @JoinColumn(name = "book_id")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "author_id")
        }
    )
    private List<Author> authors;

    @ManyToMany(mappedBy = "books")
    private List<Order> orders;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<ShoppingCart> shoppingCarts;

    @PreRemove
    public void removeBookFromOrdersAndShoppingCarts() {
        for (Order order : orders)
            order.removeBook(this);
        /*
        for (ShoppingCart shoppingCart : shoppingCarts)
            shoppingCart.removeBook(this);
*/
    }

}
