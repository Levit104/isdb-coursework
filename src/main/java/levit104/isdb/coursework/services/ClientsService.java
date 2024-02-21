package levit104.isdb.coursework.services;

import levit104.isdb.coursework.exceptions.EntityNotFoundException;
import levit104.isdb.coursework.models.Client;
import levit104.isdb.coursework.repos.ClientsRepository;
import levit104.isdb.coursework.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClientsService {
    private final ClientsRepository clientsRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Client> findAll() {
        return clientsRepository.findAll();
    }

    public Client findById(Integer id) {
        return clientsRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с id=" + id + " не найден"));
    }

    @Transactional
    public void updateById(Integer id, Client updated) {
        updated.setId(id);
        save(updated);
    }

    @Transactional
    public void deleteById(Integer id) {
        clientsRepository.deleteById(id);
    }

    @Transactional
    public void save(Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client.setRole(SecurityUtils.ROLE_USER_CLIENT);
        clientsRepository.save(client);
    }
}
