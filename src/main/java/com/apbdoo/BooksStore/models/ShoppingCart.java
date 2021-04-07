package com.apbdoo.BooksStore.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "shopping_cart")
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private int quantity;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "shopping_books", joinColumns = {
            @JoinColumn(name = "shoppingcart_id")}, inverseJoinColumns = {
            @JoinColumn(name = "book_id")
    })
    private List<Book> books;
}
