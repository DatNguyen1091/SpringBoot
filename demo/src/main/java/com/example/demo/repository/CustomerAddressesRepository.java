package com.example.demo.repository;

import com.example.demo.entity.CustomerAddresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAddressesRepository extends JpaRepository<CustomerAddresses, Long>{
}
