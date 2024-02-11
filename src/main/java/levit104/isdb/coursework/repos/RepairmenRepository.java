package levit104.isdb.coursework.repos;

import levit104.isdb.coursework.models.Repairman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepairmenRepository extends JpaRepository<Repairman, Integer> {
    Optional<Repairman> findByEmail(String email);

    Optional<Repairman> findByTelNumber(String username);

    Optional<Repairman> findByUsername(String username);
}
