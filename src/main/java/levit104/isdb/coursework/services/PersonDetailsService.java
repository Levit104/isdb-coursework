package levit104.isdb.coursework.services;

import levit104.isdb.coursework.models.Client;
import levit104.isdb.coursework.models.Repairman;
import levit104.isdb.coursework.repos.ClientsRepository;
import levit104.isdb.coursework.repos.RepairmenRepository;
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
    private final RepairmenRepository repairmenRepository;

    @Autowired
    public PersonDetailsService(ClientsRepository clientsRepository, RepairmenRepository repairmenRepository) {
        this.clientsRepository = clientsRepository;
        this.repairmenRepository = repairmenRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("ABOBA");

        Optional<Client> client = clientsRepository.findByUsername(username);
        if (client.isPresent()) return new PersonDetails(client.get());

        Optional<Repairman> repairman = repairmenRepository.findByUsername(username);
        if (repairman.isPresent()) return new PersonDetails(repairman.get());

        String userNotFound = "Пользователь " + username + " не найден";
        System.out.println(userNotFound);
        throw new UsernameNotFoundException(userNotFound);
    }
}
