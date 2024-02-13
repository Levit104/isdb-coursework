package levit104.isdb.coursework.services;

import levit104.isdb.coursework.exceptions.PersonNotFoundException;
import levit104.isdb.coursework.models.Client;
import levit104.isdb.coursework.repos.ClientsRepository;
import levit104.isdb.coursework.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public Client findById(int id) {
        return clientsRepository
                .findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Пользователь с id=" + id + " не найден"));
    }

    @Transactional
    public void updateById(int id, Client updated) {
        updated.setId(id);
        register(updated);
    }

    @Transactional
    public void deleteById(int id) {
        clientsRepository.deleteById(id);
    }

    @Transactional
    public void save(Client client) {
        register(client);
    }

    private void register(Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.setRole(SecurityUtils.ROLE_USER_CLIENT);
        clientsRepository.save(client);
    }
}
