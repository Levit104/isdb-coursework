package levit104.isdb.coursework.services;

import levit104.isdb.coursework.models.Day;
import levit104.isdb.coursework.models.Repairman;
import levit104.isdb.coursework.repos.DaysRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DaysService {
    private final DaysRepository daysRepository;
    private final RepairmenService repairmenService;

    public List<Day> findAll() {
        return daysRepository.findAll();
    }

    public List<Day> findAllByRepairmanId(Integer repairmanId) {
        return daysRepository.findAllByRepairmen_Id(repairmanId);
    }

    public List<Integer> getDaysIds(Integer repairmanId) {
        return findAllByRepairmanId(repairmanId)
                .stream()
                .map(Day::getId)
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveSchedule(Integer repairmanId, List<Integer> selectedDaysIds) {
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
