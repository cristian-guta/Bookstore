package com.apbdoo.BooksStore.models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "shopping_books", schema = "storedb", catalog = "")
public class ShoppingBooksEntity {
    private int shoppingcartId;
    private int bookId;

    @Basic
    @Column(name = "shoppingcart_id")
    public int getShoppingcartId() {
        return shoppingcartId;
    }

    public void setShoppingcartId(int shoppingcartId) {
        this.shoppingcartId = shoppingcartId;
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
        ShoppingBooksEntity that = (ShoppingBooksEntity) o;
        return shoppingcartId == that.shoppingcartId &&
                bookId == that.bookId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(shoppingcartId, bookId);
    }
}
