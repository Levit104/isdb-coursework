package levit104.isdb.coursework.services;

import levit104.isdb.coursework.models.ApplianceType;
import levit104.isdb.coursework.repos.ApplianceTypesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApplianceTypesService {
    private final ApplianceTypesRepository applianceTypesRepository;

    public List<ApplianceType> findAll() {
        return applianceTypesRepository.findAll();
    }

    @Transactional
    public void save(ApplianceType applianceType) {
        applianceTypesRepository.save(applianceType);
    }
}
