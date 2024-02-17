package levit104.isdb.coursework.services;

import levit104.isdb.coursework.repos.PeopleRepository;
import levit104.isdb.coursework.security.PersonDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonDetailsService implements UserDetailsService {
    private final PeopleRepository peopleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return peopleRepository.findByUsername(username)
                .map(PersonDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь " + username + " не найден"));
    }
}
