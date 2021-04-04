package com.apbdoo.BooksStore.models;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column
    private Double totalPrice;

    @Column
    private LocalDate orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToMany
    @JoinTable(name = "order_books",
            joinColumns = {
                    @JoinColumn(name = "order_id")}, inverseJoinColumns = {
            @JoinColumn(name = "book_id")})
    private List<Book> books;

    public void removeBook(Book book) {
        books.remove(book);
    }

    public Order() {

    }

    public Order(int id, Double totalPrice) {
        this.id = id;
        this.totalPrice = totalPrice;
    }

}
