package levit104.isdb.coursework.validation;

import levit104.isdb.coursework.models.Client;
import levit104.isdb.coursework.services.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

import static levit104.isdb.coursework.validation.ErrorMessages.*;

@Component
public class ClientValidator implements Validator {
    private final ClientsService clientsService;

    @Autowired
    public ClientValidator(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Client.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Client client = (Client) target;
        Optional<Client> clientFromDB;

        clientFromDB = clientsService.findByEmail(client.getEmail());
        if (clientFromDB.isPresent())
            errors.rejectValue("email", "", EMAIL_TAKEN);

        clientFromDB = clientsService.findByTelNumber(client.getTelNumber());
        if (clientFromDB.isPresent())
            errors.rejectValue("telNumber", "", TEL_NUMBER_TAKEN);

        clientFromDB = clientsService.findByUsername(client.getUsername());
        if (clientFromDB.isPresent())
            errors.rejectValue("username", "", USERNAME_TAKEN);
    }
}
