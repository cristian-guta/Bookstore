package com.apbdoo.BooksStore.repositories;


import com.apbdoo.BooksStore.models.Author;
import com.apbdoo.BooksStore.models.Book;
import com.apbdoo.BooksStore.models.ShoppingCart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Integer> {
    List<ShoppingCart> findByUserId(int id);
    List<ShoppingCart> findByUserIdAndBook(int id, Book book);
    List<ShoppingCart> findByUserUsernameAndBook(String username, Book book);

 //   ShoppingCart findByUserIdAndOrderDate(int id, LocalDate date);
    ShoppingCart findById(int id);
    @Query("select c from ShoppingCart c where c.user.username = :username")
    Page<ShoppingCart> findAllPage(@Param("username") String userName, Pageable pageable);
    //ShoppingCart findByUserIdAndBooksIn(int id, List<Book> books);
 //   ShoppingCart findByOrderId(int id);
   // List<ShoppingCart> findByBooks(Book book);
}
