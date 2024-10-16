package levit104.isdb.coursework.services;

import levit104.isdb.coursework.exceptions.EntityNotFoundException;
import levit104.isdb.coursework.models.Repairman;
import levit104.isdb.coursework.repos.RepairmenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RepairmenService {
    private final RepairmenRepository repairmenRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Repairman> findAll() {
        return repairmenRepository.findAll();
    }

    public List<Repairman> getAllWithNonEmptySchedule() {
        return findAll().stream()
                .filter(repairman -> !repairman.getDays().isEmpty())
                .toList();
    }

    public Repairman findById(Integer id) {
        return repairmenRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с id=" + id + " не найден"));
    }

    public Repairman findByIdForOrder(Integer id) {
        return id == null ? null : findById(id);
    }

    @Transactional
    public void deleteById(Integer id) {
        repairmenRepository.deleteById(id);
    }

    @Transactional
    public void save(Repairman repairman) {
        repairman.setPassword(passwordEncoder.encode(repairman.getPassword()));
        repairman.setRole("ROLE_USER_REPAIRMAN");
        repairmenRepository.save(repairman);
    }
}
