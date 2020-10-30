package com.food.order.service.impl;

import com.food.order.repository.AddressRepository;
import com.food.order.service.IAddressService;
import com.food.order.storage.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AddressService implements IAddressService {
    @Autowired
    AddressRepository addressRepository;

    @Override
    public List<Address> addAddress(Address address) {
        Address savedAddress = addressRepository.save(address);
        selectAddress(savedAddress.getId());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return addressRepository.findByEmail(email);
    }

    @Override
    public List<Address> getAddress() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return addressRepository.findByEmail(email);
    }

    @Override
    public Address getSelectedAddress() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return addressRepository.findByEmailAndSelected(email,true);
    }

    @Override
    public void selectAddress(String id) {
        Address address = addressRepository.findById(id).orElse(null);
        if(address != null) {
            Address selectedAddress = getSelectedAddress();
            if(selectedAddress!=null){
                selectedAddress.setSelected(false);
                addressRepository.save(selectedAddress);
            }

            address.setSelected(true);
            addressRepository.save(address);
        }
    }

    @Override
    public void deleteAddress(String id) {
        addressRepository.deleteById(id);
    }

}
