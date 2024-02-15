package levit104.isdb.coursework.services;

import levit104.isdb.coursework.exceptions.EntityNotFoundException;
import levit104.isdb.coursework.models.Appliance;
import levit104.isdb.coursework.repos.AppliancesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AppliancesService {
    private final AppliancesRepository appliancesRepository;
    private final ClientsService clientsService;

    @Autowired
    public AppliancesService(AppliancesRepository appliancesRepository, ClientsService clientsService) {
        this.appliancesRepository = appliancesRepository;
        this.clientsService = clientsService;
    }

    public List<Appliance> findAllByOwnerId(int ownerId) {
        return appliancesRepository.findAllByOwnerId(ownerId);
    }

    public Appliance findById(int id) {
        return appliancesRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Прибор с id=" + id + " не найден"));
    }

    @Transactional
    public void save(Appliance appliance, int ownerId) {
        appliance.setOwner( clientsService.findById(ownerId) );
        appliancesRepository.save(appliance);
    }
}
