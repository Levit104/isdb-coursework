package levit104.isdb.coursework.services;

import levit104.isdb.coursework.models.Repairman;
import levit104.isdb.coursework.repos.RepairmenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static levit104.isdb.coursework.security.SecurityUtils.ROLE_USER_REPAIRMAN;

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

    // TODO throw Exception
    public Repairman findById(int id) {
        return repairmenRepository.findById(id).orElse(null);
    }

    @Transactional
    public void updateById(int id, Repairman updated) {
        updated.setId(id);
        save(updated);
    }

    @Transactional
    public void deleteById(int id) {
        repairmenRepository.deleteById(id);
    }

    @Transactional
    public void register(Repairman repairman) {
        save(repairman);
    }

    private void save(Repairman repairman) {
        repairman.setPassword(passwordEncoder.encode(repairman.getPassword()));
        repairman.setRole(ROLE_USER_REPAIRMAN);
        repairmenRepository.save(repairman);
    }
}
