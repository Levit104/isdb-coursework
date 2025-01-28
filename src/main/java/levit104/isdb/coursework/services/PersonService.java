package levit104.isdb.coursework.services;

import levit104.isdb.coursework.models.Person;

import java.util.Optional;

public interface PersonService {
    Optional<Person> findByEmail(String email);

    Optional<Person> findByTelNumber(String telNumber);

    Optional<Person> findByUsername(String username);
}
