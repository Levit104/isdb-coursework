package levit104.isdb.coursework.repos;

import levit104.isdb.coursework.models.Repairman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairmenRepository extends JpaRepository<Repairman, Integer> {
}
