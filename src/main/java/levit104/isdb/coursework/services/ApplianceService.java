package levit104.isdb.coursework.services;

import levit104.isdb.coursework.models.Appliance;
import levit104.isdb.coursework.models.ApplianceType;

import java.util.List;
import java.util.Optional;

public interface ApplianceService {
    List<ApplianceType> getAllTypes();

    List<Appliance> getAllByOwnerId(Integer ownerId);

    Appliance getById(Integer id);

    Optional<Appliance> findByName(String name);

    void save(Appliance appliance);

    boolean deleteById(Integer id);
}
