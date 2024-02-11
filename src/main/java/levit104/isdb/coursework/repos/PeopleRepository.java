package levit104.isdb.coursework.repos;

import levit104.isdb.coursework.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByEmail(String email);

    Optional<Person> findByTelNumber(String username);

    Optional<Person> findByUsername(String username);
}
