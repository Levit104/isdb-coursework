package levit104.isdb.coursework.services;

import levit104.isdb.coursework.models.Person;
import levit104.isdb.coursework.repos.PeopleRepository;
import levit104.isdb.coursework.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    public boolean hasCorrectId(int id) {
        return id == SecurityUtils.getAuthenticatedPerson().getId();
    }

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public Optional<Person> findByEmail(String email) {
        return peopleRepository.findByEmail(email);
    }

    public Optional<Person> findByTelNumber(String telNumber) {
        return peopleRepository.findByTelNumber(telNumber);
    }

    public Optional<Person> findByUsername(String username) {
        return peopleRepository.findByUsername(username);
    }
}
