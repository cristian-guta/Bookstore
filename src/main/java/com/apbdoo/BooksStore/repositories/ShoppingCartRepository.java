package com.apbdoo.BooksStore.repositories;


import com.apbdoo.BooksStore.models.ShoppingCart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Integer> {
    ShoppingCart findByUserId(int id);

    ShoppingCart findByUserUsername(String username);

    ShoppingCart findById(int id);

    @Query("select c from ShoppingCart c where c.user.username = :username")
    Page<ShoppingCart> findAllPage(@Param("username") String userName, Pageable pageable);
}
