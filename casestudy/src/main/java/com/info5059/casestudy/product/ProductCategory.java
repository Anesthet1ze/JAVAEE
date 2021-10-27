package com.info5059.casestudy.product;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Entity class to keep track of Expense types
 */
@Entity
@Data
@RequiredArgsConstructor
public class ProductCategory {
    @Id
    private String id;
    private String name;
}