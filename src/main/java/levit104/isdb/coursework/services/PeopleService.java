package levit104.isdb.coursework.services;

import levit104.isdb.coursework.models.Person;
import levit104.isdb.coursework.repos.PeopleRepository;
import levit104.isdb.coursework.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PeopleService {
    private final PeopleRepository peopleRepository;

    public boolean hasCorrectId(Integer id) {
        return Objects.equals(id, SecurityUtils.getAuthenticatedPerson().getId());
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
