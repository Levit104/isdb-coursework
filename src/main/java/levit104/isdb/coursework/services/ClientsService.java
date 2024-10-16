package levit104.isdb.coursework.services;

import levit104.isdb.coursework.models.Client;
import levit104.isdb.coursework.repos.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ClientsService {
    private final ClientsRepository clientsRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ClientsService(ClientsRepository clientsRepository, PasswordEncoder passwordEncoder) {
        this.clientsRepository = clientsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Client> findByEmail(String email) {
        return clientsRepository.findByEmail(email);
    }

    public Optional<Client> findByTelNumber(String telNumber) {
        return clientsRepository.findByTelNumber(telNumber);
    }

    public Optional<Client> findByUsername(String username) {
        return clientsRepository.findByUsername(username);
    }

    @Transactional
    public void register(Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.setRole("ROLE_USER_CLIENT");
        clientsRepository.save(client);
    }
}
