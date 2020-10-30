package com.food.order.service;

import com.food.order.storage.Address;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IAddressService {
    List<Address> addAddress(Address address);
    List<Address> getAddress();
    Address getSelectedAddress();
    void selectAddress(String id);
    void deleteAddress(String id);
}
