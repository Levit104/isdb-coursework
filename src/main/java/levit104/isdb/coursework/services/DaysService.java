package levit104.isdb.coursework.services;

import levit104.isdb.coursework.models.Day;
import levit104.isdb.coursework.models.Repairman;
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
    private final RepairmenService repairmenService;

    @Autowired
    public DaysService(DaysRepository daysRepository, RepairmenService repairmenService) {
        this.daysRepository = daysRepository;
        this.repairmenService = repairmenService;
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

    @Transactional
    public void saveSchedule(int repairmanId, List<Integer> selectedDaysIds) {
        Repairman repairman = repairmenService.findById(repairmanId);
        List<Day> days = convertDaysIdsToDays(selectedDaysIds);
        repairman.setDays(days);
    }

    private List<Day> convertDaysIdsToDays(List<Integer> daysIds) {
        return daysIds.stream().map(dayId -> {
            Day day = new Day();
            day.setId(dayId);
            return day;
        }).collect(Collectors.toList());
    }
}
