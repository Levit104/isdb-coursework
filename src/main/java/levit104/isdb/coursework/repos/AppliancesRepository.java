package levit104.isdb.coursework.repos;

import levit104.isdb.coursework.models.Appliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppliancesRepository extends JpaRepository<Appliance, Integer> {
    List<Appliance> findAllByOwnerId(Integer id);

    Optional<Appliance> findByName(String name);
}
