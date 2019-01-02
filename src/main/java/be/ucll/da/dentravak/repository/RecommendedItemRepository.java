package be.ucll.da.dentravak.repository;

import be.ucll.da.dentravak.controller.RecommendedItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface RecommendedItemRepository extends CrudRepository<RecommendedItem, UUID> {

    List<RecommendedItem> findAllByEmailAddress(String emailAddress);

    List<RecommendedItem> findAllByRating(String emailAddress);


}
