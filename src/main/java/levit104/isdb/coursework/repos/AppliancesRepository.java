package levit104.isdb.coursework.repos;

import levit104.isdb.coursework.models.Appliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppliancesRepository extends JpaRepository<Appliance, Integer> {
    List<Appliance> findAllByOwnerId(int id);
}
