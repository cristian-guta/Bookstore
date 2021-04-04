package com.apbdoo.BooksStore.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "book_info", schema = "storedb", catalog = "")
public class BookInfoEntity {
    private int id;
    private String bookisbn;
    private Date bookPublicationDate;
    private String bookTitle;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "bookisbn")
    public String getBookisbn() {
        return bookisbn;
    }

    public void setBookisbn(String bookisbn) {
        this.bookisbn = bookisbn;
    }

    @Basic
    @Column(name = "book_publication_date")
    public Date getBookPublicationDate() {
        return bookPublicationDate;
    }

    public void setBookPublicationDate(Date bookPublicationDate) {
        this.bookPublicationDate = bookPublicationDate;
    }

    @Basic
    @Column(name = "book_title")
    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookInfoEntity that = (BookInfoEntity) o;
        return id == that.id &&
                Objects.equals(bookisbn, that.bookisbn) &&
                Objects.equals(bookPublicationDate, that.bookPublicationDate) &&
                Objects.equals(bookTitle, that.bookTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookisbn, bookPublicationDate, bookTitle);
    }
}
