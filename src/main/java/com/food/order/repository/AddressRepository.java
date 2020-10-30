package com.food.order.repository;

import com.food.order.storage.Address;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AddressRepository extends MongoRepository<Address, String> {
     List<Address> findByEmail(String email);
     Address findByEmailAndSelected(String email, boolean selected);
}
