package levit104.isdb.coursework.services;

import levit104.isdb.coursework.exceptions.EntityNotFoundException;
import levit104.isdb.coursework.models.Appliance;
import levit104.isdb.coursework.models.Client;
import levit104.isdb.coursework.repos.AppliancesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AppliancesService {
    private final AppliancesRepository appliancesRepository;
    private final ClientsService clientsService;

    public List<Appliance> findAllByOwnerId(Integer ownerId) {
        return appliancesRepository.findAllByOwnerId(ownerId);
    }

    public Appliance findById(Integer id) {
        return appliancesRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Техника с id=" + id + " не найдена"));
    }

    @Transactional
    public void save(Appliance appliance, Integer ownerId) {
        Client owner = clientsService.findById(ownerId);
        appliance.setOwner(owner);
        appliancesRepository.save(appliance);
    }

    @Transactional
    public void updateById(Integer id, Appliance appliance, Integer ownerId) {
        appliance.setId(id);
        save(appliance, ownerId);
    }

    @Transactional
    public void deleteById(Integer id) {
        appliancesRepository.deleteById(id);
    }
}
