package be.ucll.da.dentravak.repository;

import be.ucll.da.dentravak.model.SandwichOrder;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SandwichOrderRepository extends CrudRepository<SandwichOrder,UUID> {


}
