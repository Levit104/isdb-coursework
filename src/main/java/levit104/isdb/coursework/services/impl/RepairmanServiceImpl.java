package levit104.isdb.coursework.services.impl;

import levit104.isdb.coursework.exceptions.EntityNotFoundException;
import levit104.isdb.coursework.models.Repairman;
import levit104.isdb.coursework.repos.RepairmenRepository;
import levit104.isdb.coursework.services.RepairmanService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RepairmanServiceImpl implements RepairmanService {
    private final RepairmenRepository repairmenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Repairman> getAll() {
        return repairmenRepository.findAll();
    }

    @Override
    public List<Repairman> getAllWithNonEmptySchedule() {
        return getAll().stream()
                .filter(repairman -> !repairman.getDays().isEmpty())
                .toList();
    }

    @Override
    public Repairman getById(Integer id) {
        return repairmenRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с id=" + id + " не найден"));
    }

    @Override
    public Repairman getByIdForOrder(Integer id) {
        return id == null ? null : getById(id);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        repairmenRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void save(Repairman repairman) {
        repairman.setPassword(passwordEncoder.encode(repairman.getPassword()));
        repairman.setRole("ROLE_USER_REPAIRMAN");
        repairmenRepository.save(repairman);
    }
}
