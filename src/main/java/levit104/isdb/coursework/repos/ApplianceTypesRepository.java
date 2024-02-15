package levit104.isdb.coursework.repos;

import levit104.isdb.coursework.models.ApplianceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplianceTypesRepository extends JpaRepository<ApplianceType, Integer> {
}
