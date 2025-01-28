package levit104.isdb.coursework.validation;

import levit104.isdb.coursework.models.Person;
import levit104.isdb.coursework.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;
import java.util.Optional;

import static levit104.isdb.coursework.validation.ErrorMessages.*;

@Component
@RequiredArgsConstructor
public class PersonValidator implements Validator {
    private final PersonService personService;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        Person person = (Person) target;
        Optional<Person> personFromDB;

        // проверки ID нужны для корректной работы обновления профиля
        personFromDB = personService.findByEmail(person.getEmail());
        if (personFromDB.isPresent() && !Objects.equals(person.getId(), personFromDB.get().getId()))
            errors.rejectValue("email", "", EMAIL_TAKEN);

        personFromDB = personService.findByTelNumber(person.getTelNumber());
        if (personFromDB.isPresent() && !Objects.equals(person.getId(), personFromDB.get().getId()))
            errors.rejectValue("telNumber", "", TEL_NUMBER_TAKEN);

        personFromDB = personService.findByUsername(person.getUsername());
        if (personFromDB.isPresent() && !Objects.equals(person.getId(), personFromDB.get().getId()))
            errors.rejectValue("username", "", USERNAME_TAKEN);
    }
}
