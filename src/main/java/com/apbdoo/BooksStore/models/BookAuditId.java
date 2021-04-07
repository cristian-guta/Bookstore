package com.apbdoo.BooksStore.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookAuditId implements Serializable {
    private int id;
    private int rev;
}
