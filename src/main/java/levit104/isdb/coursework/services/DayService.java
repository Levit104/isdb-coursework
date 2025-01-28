package levit104.isdb.coursework.services;

import levit104.isdb.coursework.models.Day;
import levit104.isdb.coursework.models.Repairman;

import java.util.List;

public interface DayService {
    List<Day> getAll();

    List<Day> getAllByRepairmanId(Integer repairmanId);

    List<Integer> getDaysIds(Integer repairmanId);

    void saveSchedule(Repairman repairman, List<Integer> selectedDaysIds);
}
