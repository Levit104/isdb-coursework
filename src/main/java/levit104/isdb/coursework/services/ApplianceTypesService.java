package levit104.isdb.coursework.services;

import levit104.isdb.coursework.models.ApplianceType;
import levit104.isdb.coursework.repos.ApplianceTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ApplianceTypesService {
    private final ApplianceTypesRepository applianceTypesRepository;

    @Autowired
    public ApplianceTypesService(ApplianceTypesRepository applianceTypesRepository) {
        this.applianceTypesRepository = applianceTypesRepository;
    }

    public List<ApplianceType> findAll() {
        return applianceTypesRepository.findAll();
    }

    @Transactional
    public void save(ApplianceType applianceType) {
        applianceTypesRepository.save(applianceType);
    }
}
