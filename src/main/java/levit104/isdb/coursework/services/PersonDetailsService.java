package levit104.isdb.coursework.services;

import levit104.isdb.coursework.models.Client;
import levit104.isdb.coursework.repos.ClientsRepository;
import levit104.isdb.coursework.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final ClientsRepository clientsRepository;

    @Autowired
    public PersonDetailsService(ClientsRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> client = clientsRepository.findByUsername(username);
        System.out.println("ABOBA");

        if (client.isEmpty())
            throw new UsernameNotFoundException("Пользователь " + username + " не найден");

        return new PersonDetails(client.get());
    }
}
