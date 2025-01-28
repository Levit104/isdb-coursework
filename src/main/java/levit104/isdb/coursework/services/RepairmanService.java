package levit104.isdb.coursework.services;

import levit104.isdb.coursework.models.Repairman;

import java.util.List;

public interface RepairmanService {
    List<Repairman> getAll();

    List<Repairman> getAllWithNonEmptySchedule();

    Repairman getById(Integer id);

    Repairman getByIdForOrder(Integer id);

    void deleteById(Integer id);

    void save(Repairman repairman);
}
