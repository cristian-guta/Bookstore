package com.apbdoo.BooksStore.models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "authors_books", schema = "storedb", catalog = "")
public class AuthorsBooksEntity {
    private int bookId;
    private int authorId;

    @Basic
    @Column(name = "book_id")
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Basic
    @Column(name = "author_id")
    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorsBooksEntity that = (AuthorsBooksEntity) o;
        return bookId == that.bookId &&
                authorId == that.authorId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, authorId);
    }
}
