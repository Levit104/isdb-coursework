package levit104.isdb.coursework.services.impl;

import levit104.isdb.coursework.exceptions.EntityNotFoundException;
import levit104.isdb.coursework.models.Appliance;
import levit104.isdb.coursework.models.ApplianceType;
import levit104.isdb.coursework.repos.ApplianceTypesRepository;
import levit104.isdb.coursework.repos.AppliancesRepository;
import levit104.isdb.coursework.services.ApplianceService;
import levit104.isdb.coursework.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplianceServiceImpl implements ApplianceService {
    private final AppliancesRepository appliancesRepository;
    private final ApplianceTypesRepository applianceTypesRepository;
    private final ImageService imageService;

    @Override
    public List<ApplianceType> getAllTypes() {
        return applianceTypesRepository.findAll();
    }

    @Override
    public List<Appliance> getAllByOwnerId(Integer ownerId) {
        var appliances = appliancesRepository.findAllByOwnerId(ownerId);
        appliances.forEach(appliance ->
                appliance.setImageName(imageService.generateImageUrl(appliance.getImageName())));
        return appliances;
    }

    @Override
    public Appliance getById(Integer id) {
        var appliance = getByIdWithRawImage(id);
        appliance.setImageName(imageService.generateImageUrl(appliance.getImageName()));
        return appliance;
    }

    private Appliance getByIdWithRawImage(Integer id) {
        return appliancesRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Техника с id=" + id + " не найдена"));
    }

    @Override
    public Optional<Appliance> findByName(String name) {
        return appliancesRepository.findByName(name);
    }

    @Override
    @Transactional
    public void save(Appliance appliance) {
        var image = appliance.getImage();

        if (image != null && !image.isEmpty()) {
            appliance.setImageName(imageService.save(image));
        }

        if (appliance.getId() != null) { // обновление существующего
            var oldImageName = getByIdWithRawImage(appliance.getId()).getImageName();

            if (oldImageName != null && appliance.getImageName() == null) {
                appliance.setImageName(oldImageName);
            }
        }

        appliancesRepository.save(appliance);
    }

    @Override
    @Transactional
    public boolean deleteById(Integer id) {
        Appliance appliance = getById(id);

        for (var order : appliance.getOrders()) {
            if (order.isActive()) {
                return true;
            }

            order.setAppliance(null);
        }

        appliancesRepository.delete(appliance);
        return false;
    }
}
