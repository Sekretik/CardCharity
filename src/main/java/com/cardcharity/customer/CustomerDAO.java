package com.cardcharity.customer;

import com.cardcharity.IDao;
import com.cardcharity.exception.QueryException;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerDAO implements IDao<Customer> {
    @Autowired
    FirebaseApp firebaseApp;

    @Autowired
    CustomerRepository repository;

    public void increaseUseCount(Customer customer) {
        int useCount = customer.getUseCount();
        useCount += 1;
        customer.setUseCount(useCount);
    }

    public Customer getFromFirebase(String uid) {
        UserRecord userRecord;
        try {
            userRecord = FirebaseAuth.getInstance(firebaseApp).getUser(uid);
        } catch (FirebaseAuthException e) {
            return null;
        }
        Customer customer = new Customer(userRecord.getUid(), userRecord.getEmail());
        return customer;
    }

    public void save(Customer customer){
        repository.save(customer);
    }

    @Override
    public void update(Customer customer) throws QueryException {

    }

    public List<Customer> getAll() {
        return repository.findAll();
    }

    public Customer findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Customer> findAll() {
        return repository.findAll();
    }

    public List<Customer> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Optional<Customer> findByUid(String uid) {
        return repository.findOneByUid(uid);
    }
}
