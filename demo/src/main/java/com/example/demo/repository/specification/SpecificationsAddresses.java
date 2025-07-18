package com.example.demo.repository.specification;

import com.example.demo.entity.Addresses;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationsAddresses {
    public static Specification<Addresses> isNotDeleted() {
        return (root, query, cb) -> cb.isFalse(root.get("isDeleted"));
    }
}
