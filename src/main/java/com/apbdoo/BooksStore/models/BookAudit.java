package com.apbdoo.BooksStore.models;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "book_aud")
@Data
@Accessors(chain = true)
@IdClass(BookAuditId.class)
public class BookAudit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int id;

    @Id
    private int rev;
}
