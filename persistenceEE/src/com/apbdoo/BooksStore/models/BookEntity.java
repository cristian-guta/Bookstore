package com.apbdoo.BooksStore.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "book", schema = "storedb", catalog = "")
public class BookEntity {
    private int id;
    private double price;
    private Integer bookcategoryId;
    private Integer bookInfoId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "bookcategory_id")
    public Integer getBookcategoryId() {
        return bookcategoryId;
    }

    public void setBookcategoryId(Integer bookcategoryId) {
        this.bookcategoryId = bookcategoryId;
    }

    @Basic
    @Column(name = "book_info_id")
    public Integer getBookInfoId() {
        return bookInfoId;
    }

    public void setBookInfoId(Integer bookInfoId) {
        this.bookInfoId = bookInfoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookEntity that = (BookEntity) o;
        return id == that.id &&
                Double.compare(that.price, price) == 0 &&
                Objects.equals(bookcategoryId, that.bookcategoryId) &&
                Objects.equals(bookInfoId, that.bookInfoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, bookcategoryId, bookInfoId);
    }
}
