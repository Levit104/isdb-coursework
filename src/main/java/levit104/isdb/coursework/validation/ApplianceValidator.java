package levit104.isdb.coursework.validation;

import levit104.isdb.coursework.models.Appliance;
import levit104.isdb.coursework.services.AppliancesService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ApplianceValidator implements Validator {
    private final AppliancesService appliancesService;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Appliance.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        Appliance appliance = (Appliance) target;
        Optional<Appliance> applianceFromDB = appliancesService.findByName(appliance.getName());

        if (applianceFromDB.isPresent()
                && Objects.equals(appliance.getOwner().getId(), applianceFromDB.get().getOwner().getId())
                && !Objects.equals(appliance.getId(), applianceFromDB.get().getId())
        ) {
            errors.rejectValue("name", "", ErrorMessages.APPLIANCE_EXISTS);
        }

        if (appliance.getPurchaseDate() != null && appliance.getPurchaseDate().isAfter(LocalDate.now())) {
            errors.rejectValue("purchaseDate", "", ErrorMessages.INVALID_PURCHASE_DATE);
        }

    }
}
