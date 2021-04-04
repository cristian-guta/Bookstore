package com.apbdoo.BooksStore.repositories;


import com.apbdoo.BooksStore.models.BookCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCategoryRepository extends CrudRepository<BookCategory, Integer> {
    BookCategory findByBookCategoryDescription(String bookCategoryDescription);
    List<BookCategory> findByIdIn(int[] ids);
}
