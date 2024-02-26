package levit104.isdb.coursework.services;

import levit104.isdb.coursework.exceptions.EntityNotFoundException;
import levit104.isdb.coursework.models.Appliance;
import levit104.isdb.coursework.models.ApplianceType;
import levit104.isdb.coursework.repos.ApplianceTypesRepository;
import levit104.isdb.coursework.repos.AppliancesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AppliancesService {
    private final AppliancesRepository appliancesRepository;
    private final ApplianceTypesRepository applianceTypesRepository;

    public List<ApplianceType> findAllTypes() {
        return applianceTypesRepository.findAll();
    }

    public List<Appliance> findAllByOwnerId(Integer ownerId) {
        return appliancesRepository.findAllByOwnerId(ownerId);
    }

    public Appliance findById(Integer id) {
        return appliancesRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Техника с id=" + id + " не найдена"));
    }

    public Optional<Appliance> findByName(String name) {
        return appliancesRepository.findByName(name);
    }

    @Transactional
    public void save(Appliance appliance) {
        appliancesRepository.save(appliance);
    }

    @Transactional
    public void updateById(Integer id, Appliance appliance) {
        appliance.setId(id);
        save(appliance);
    }

    @Transactional
    public void deleteById(Integer id) {
        appliancesRepository.deleteById(id);
    }
}
