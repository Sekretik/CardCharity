package com.cardcharity.customer;

import com.cardcharity.base.IDao;
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
    CustomerRepository customerRepository;

    public void increaseUseCount(Customer customer) {
        int useCount = customer.getUseCount();
        useCount += 1;
        customer.setUseCount(useCount);
    }

    public Customer getFromFirebase(String uid) {
        UserRecord userRecord = null;
        try {
            userRecord = FirebaseAuth.getInstance(firebaseApp).getUser(uid);
        } catch (FirebaseAuthException e) {
            return null;
        }
        Customer customer = new Customer(userRecord.getUid(), userRecord.getEmail());
        return customer;
    }

    @Override
    public void save(Customer customer){
        customerRepository.save(customer);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public void update(Customer object) throws Exception {

    }

    @Override
    public Optional<Customer> findById(long id) {
        return customerRepository.findById(id);
    }

    public List<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Optional<Customer> findByUid(String uid) {
        return customerRepository.findOneByUid(uid);
    }
}
