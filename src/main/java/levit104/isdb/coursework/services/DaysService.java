package levit104.isdb.coursework.services;

import levit104.isdb.coursework.models.Day;
import levit104.isdb.coursework.repos.DaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
