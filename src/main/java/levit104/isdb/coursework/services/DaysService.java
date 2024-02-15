package levit104.isdb.coursework.services;

import levit104.isdb.coursework.models.Day;
import levit104.isdb.coursework.repos.DaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DaysService {
    private final DaysRepository daysRepository;

    @Autowired
    public DaysService(DaysRepository daysRepository) {
        this.daysRepository = daysRepository;
    }

    public List<Day> findAll() {
        return daysRepository.findAll();
    }

    public List<Day> findAllByRepairmanId(int repairmanId) {
        return daysRepository.findAllByRepairmen_Id(repairmanId);
    }

    public List<Integer> getDaysIds(int repairmanId) {
        return findAllByRepairmanId(repairmanId)
                .stream()
                .map(Day::getId)
                .collect(Collectors.toList());
    }
}
