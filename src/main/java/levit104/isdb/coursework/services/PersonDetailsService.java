package levit104.isdb.coursework.services;

import levit104.isdb.coursework.repos.PeopleRepository;
import levit104.isdb.coursework.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return peopleRepository.findByUsername(username)
                .map(PersonDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь " + username + " не найден"));
    }
}
