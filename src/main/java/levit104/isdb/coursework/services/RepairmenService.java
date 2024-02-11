package levit104.isdb.coursework.services;

import levit104.isdb.coursework.models.Repairman;
import levit104.isdb.coursework.repos.RepairmenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void register(Repairman repairman) {
        repairman.setPassword(passwordEncoder.encode(repairman.getPassword()));
        repairman.setRole("ROLE_USER_REPAIRMAN");
        repairmenRepository.save(repairman);
    }
}
