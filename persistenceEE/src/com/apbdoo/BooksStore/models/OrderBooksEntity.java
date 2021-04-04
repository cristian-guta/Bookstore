package com.apbdoo.BooksStore.models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "order_books", schema = "storedb", catalog = "")
public class OrderBooksEntity {
    private int orderId;
    private int bookId;

    @Basic
    @Column(name = "order_id")
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "book_id")
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderBooksEntity that = (OrderBooksEntity) o;
        return orderId == that.orderId &&
                bookId == that.bookId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, bookId);
    }
}
