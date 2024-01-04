package net.javaguides.springboot.controller;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Address;
import net.javaguides.springboot.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/addresses")

public class AddressController {
    @Autowired
    private AddressRepository addressRepository;

    @GetMapping
    public List<Address> getAllAddresses(){
        return addressRepository.findAll();
    }

    // build create address REST API
    @PostMapping
    public Address createAddress(@RequestBody Address address) {
        return addressRepository.save(address);
    }

    // build get address by id REST API
    @GetMapping("{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable long id){
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not exist with id:" + id));
        return ResponseEntity.ok(address);
    }

    // build update address REST API
    @PutMapping("{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable long id,@RequestBody Address addressDetails) {
        Address updateAddress = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not exist with id: " + id));

        updateAddress.setStreet(addressDetails.getStreet());
        updateAddress.setEmployeeId(addressDetails.getEmployeeId());

        addressRepository.save(updateAddress);

        return ResponseEntity.ok(updateAddress);
    }

    // build delete address REST API
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteAddress(@PathVariable long id){

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not exist with id: " + id));

        addressRepository.delete(address);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
