package com.apbdoo.BooksStore.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "bookcategory", schema = "storedb", catalog = "")
public class BookcategoryEntity {
    private int id;
    private String bookCategoryDescription;
    private Integer index;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "book_category_description")
    public String getBookCategoryDescription() {
        return bookCategoryDescription;
    }

    public void setBookCategoryDescription(String bookCategoryDescription) {
        this.bookCategoryDescription = bookCategoryDescription;
    }

    @Basic
    @Column(name = "_index")
    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookcategoryEntity that = (BookcategoryEntity) o;
        return id == that.id &&
                Objects.equals(bookCategoryDescription, that.bookCategoryDescription) &&
                Objects.equals(index, that.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookCategoryDescription, index);
    }
}
