package levit104.isdb.coursework.services;

import levit104.isdb.coursework.models.Client;
import levit104.isdb.coursework.repos.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static levit104.isdb.coursework.security.SecurityUtils.ROLE_USER_CLIENT;

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

    public List<Client> findAll() {
        return clientsRepository.findAll();
    }

    // TODO throw Exception
    public Client findById(int id) {
        return clientsRepository.findById(id).orElse(null);
    }

    @Transactional
    public void updateById(int id, Client updated) {
        updated.setId(id);
        save(updated);
    }

    @Transactional
    public void deleteById(int id) {
        clientsRepository.deleteById(id);
    }

    @Transactional
    public void register(Client client) {
        save(client);
    }

    private void save(Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.setRole(ROLE_USER_CLIENT);
        clientsRepository.save(client);
    }
}
