package levit104.isdb.coursework.repos;

import levit104.isdb.coursework.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientsRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByEmail(String email);
    Optional<Client> findByTelNumber(String username);
    Optional<Client> findByUsername(String username);
}
