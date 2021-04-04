package com.apbdoo.BooksStore.dto;

import com.apbdoo.BooksStore.models.Book;
import com.apbdoo.BooksStore.models.ShoppingCart;
import com.apbdoo.BooksStore.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDTO {

    private int id;

    private int quantity;

    private User user;

    private Book books;

    public  ShoppingCartDTO(ShoppingCart shoppingCart){
        this.id = shoppingCart.getId();
        this.quantity = shoppingCart.getQuantity();
        this.user = shoppingCart.getUser();
        this.books = shoppingCart.getBook();
    }
}
