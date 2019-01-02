package be.ucll.da.dentravak.repository;

import be.ucll.da.dentravak.model.Sandwich;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SandwichRepository extends CrudRepository<Sandwich,UUID> {

    Sandwich findSandwichByName(String name);

}