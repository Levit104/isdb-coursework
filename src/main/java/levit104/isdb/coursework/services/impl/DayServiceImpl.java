package levit104.isdb.coursework.services.impl;

import levit104.isdb.coursework.models.Day;
import levit104.isdb.coursework.models.Repairman;
import levit104.isdb.coursework.repos.DaysRepository;
import levit104.isdb.coursework.services.DayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DayServiceImpl implements DayService {
    private final DaysRepository daysRepository;

    @Override
    public List<Day> getAll() {
        return daysRepository.findAll();
    }

    @Override
    public List<Day> getAllByRepairmanId(Integer repairmanId) {
        return daysRepository.findAllByRepairmen_Id(repairmanId);
    }

    @Override
    public List<Integer> getDaysIds(Integer repairmanId) {
        return getAllByRepairmanId(repairmanId).stream().map(Day::getId).toList();
    }

    @Override
    @Transactional
    public void saveSchedule(Repairman repairman, List<Integer> selectedDaysIds) {
        List<Day> days = convertDaysIdsToDays(selectedDaysIds);
        repairman.setDays(days);
    }

    private List<Day> convertDaysIdsToDays(List<Integer> daysIds) {
        return daysIds.stream().map(Day::new).toList();
    }
}
