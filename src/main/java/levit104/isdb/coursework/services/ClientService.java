package levit104.isdb.coursework.services;

import levit104.isdb.coursework.models.Client;

public interface ClientService {
    Client getById(Integer id);

    void deleteById(Integer id);

    void save(Client client);
}
