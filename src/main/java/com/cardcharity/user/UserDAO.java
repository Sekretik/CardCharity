package com.cardcharity.user;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDAO {
    @Autowired
    FirebaseApp firebaseApp;

    @Autowired
    UserRepository userRepository;

    public void increaseUseCount(User user) {
        long useCount = user.getUseCount();
        useCount++;
        user.setUseCount(useCount);
    }

    public User getFromFirebase(String uid) {
        UserRecord userRecord = null;
        try {
            userRecord = FirebaseAuth.getInstance(firebaseApp).getUser(uid);
        } catch (FirebaseAuthException e) {
            return null;
        }
        User user = new User(userRecord.getUid(), userRecord.getEmail());
        return user;
    }

    public void save(User user){
        userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(long id) {
        return userRepository.findById(id);
    }

    public List<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByUid(String uid) {
        return userRepository.findOneByUid(uid);
    }
}
