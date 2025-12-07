package br.com.felipe.userserverapi.repository;

import br.com.felipe.userserverapi.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Locale;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {


    Optional<User> findByEmail(final String email);

    void deleteByEmail(String email);
}
