package levit104.isdb.coursework.services;

import levit104.isdb.coursework.exceptions.EntityNotFoundException;
import levit104.isdb.coursework.models.Day;
import levit104.isdb.coursework.models.Repairman;
import levit104.isdb.coursework.repos.RepairmenRepository;
import levit104.isdb.coursework.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class RepairmenService {
    private final RepairmenRepository repairmenRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RepairmenService(RepairmenRepository repairmenRepository, PasswordEncoder passwordEncoder) {
        this.repairmenRepository = repairmenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Repairman> findAll() {
        return repairmenRepository.findAll();
    }

    public Repairman findById(int id) {
        return repairmenRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с id=" + id + " не найден"));
    }

    @Transactional
    public void updateById(int id, Repairman updated, List<Integer> selectedDaysIds) {
        updated.setId(id);
        register(updated, selectedDaysIds);
    }

    @Transactional
    public void deleteById(int id) {
        repairmenRepository.deleteById(id);
    }

    @Transactional
    public void save(Repairman repairman, List<Integer> selectedDaysIds) {
        register(repairman, selectedDaysIds);
    }

    private void register(Repairman repairman, List<Integer> selectedDaysIds) {
        repairman.setPassword(passwordEncoder.encode(repairman.getPassword()));
        repairman.setRole(SecurityUtils.ROLE_USER_REPAIRMAN);
        repairman.setDays(selectedDaysIds.stream().map(selectedId -> {
            Day day = new Day();
            day.setId(selectedId);
            return day;
        }).collect(Collectors.toList()));
        repairmenRepository.save(repairman);
    }

    public List<Day> getSchedule(int id) {
        return findById(id).getDays();
    }

    public List<Integer> getScheduleIds(int id) {
        return getSchedule(id)
                .stream()
                .map(Day::getId)
                .collect(Collectors.toList());
    }

}
