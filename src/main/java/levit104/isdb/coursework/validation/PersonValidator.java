package levit104.isdb.coursework.validation;

import levit104.isdb.coursework.models.Person;
import levit104.isdb.coursework.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

import static levit104.isdb.coursework.validation.ErrorMessages.*;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        Optional<Person> personFromDB;

        // проверки ID нужны для корректной работы обновления профиля
        personFromDB = peopleService.findByEmail(person.getEmail());
        if (personFromDB.isPresent() && person.getId() != personFromDB.get().getId())
            errors.rejectValue("email", "", EMAIL_TAKEN);

        personFromDB = peopleService.findByTelNumber(person.getTelNumber());
        if (personFromDB.isPresent() && person.getId() != personFromDB.get().getId())
            errors.rejectValue("telNumber", "", TEL_NUMBER_TAKEN);

        personFromDB = peopleService.findByUsername(person.getUsername());
        if (personFromDB.isPresent() && person.getId() != personFromDB.get().getId())
            errors.rejectValue("username", "", USERNAME_TAKEN);
    }
}
