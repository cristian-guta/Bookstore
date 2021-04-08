package com.apbdoo.BooksStore.repositories;


import com.apbdoo.BooksStore.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "INSERT INTO user_favourite_books(user_id, favourite_books_id) VALUES (:user_id, :favourite_books_id)")
    void addFavouriteBook(@Param("user_id") int userId,
                          @Param("favourite_books_id") int favouriteBooksId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "DELETE FROM user_favourite_books WHERE user_id =:user_id AND favourite_books_id =:favourite_books_id")
    void deleteFavouriteBook(@Param("user_id") int userId,
                          @Param("favourite_books_id") int favouriteBooksId);

    @Query(nativeQuery = true,
            value = "SELECT favourite_books_id FROM user_favourite_books WHERE user_id =:user_id")
    List<Integer> getFavouriteBooks(@Param("user_id") int userId);
}