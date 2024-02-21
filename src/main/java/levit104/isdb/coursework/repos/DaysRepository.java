package levit104.isdb.coursework.repos;

import levit104.isdb.coursework.models.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DaysRepository extends JpaRepository<Day, Integer> {
    List<Day> findAllByRepairmen_Id(Integer repairmanId);
}
