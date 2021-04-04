package com.apbdoo.BooksStore.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "shopping_cart", schema = "storedb", catalog = "")
public class ShoppingCartEntity {
    private int id;
    private int quantity;
    private Integer userId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCartEntity that = (ShoppingCartEntity) o;
        return id == that.id &&
                quantity == that.quantity &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, userId);
    }
}
