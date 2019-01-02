package be.ucll.da.dentravak;

import be.ucll.da.dentravak.controller.RecommendedItem;
import be.ucll.da.dentravak.model.*;
import be.ucll.da.dentravak.repository.RecommendedItemRepository;
import be.ucll.da.dentravak.repository.SandwichOrderRepository;
import be.ucll.da.dentravak.repository.SandwichRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class Application {

    public static LocalDateTime now = LocalDateTime.now();

    public static void main(String[] args) {
        new SpringApplicationBuilder(ServiceDiscoveryApplication.class)
                .web(true).run(args);
    }

    @Bean
    public CommandLineRunner demo(SandwichRepository sandwichRepository, SandwichOrderRepository sandwichOrderRepository, RecommendedItemRepository recommendedItemRepository) {
        return (args) -> {
            // save a couple of sandwiches
            sandwichRepository.save(Sandwich.SandwichBuilder.aSandwich().withName("Smos").withIngredients("Kaas,Hesp,Sla,Tomaat,Ei").withPrice(new BigDecimal("3.20")).build());
            sandwichRepository.save(Sandwich.SandwichBuilder.aSandwich().withName("Martino").withIngredients("Américain,Ui,Augurk,Martinosaus").withPrice(new BigDecimal("3.20")).build());
            sandwichRepository.save(Sandwich.SandwichBuilder.aSandwich().withName("Ei en spek").withIngredients("Eieren,spek").withPrice(new BigDecimal("4.20")).build());
            System.out.println(now);
            sandwichOrderRepository.save(SandwichOrder.SandwichOrderBuilder.anOrder().withSandwichName("Smos").withBreadType(BreadTypeEnum.BOTERHAMMEKES).withMobilePhoneNumber("0498/157802").build());

            UUID smosID = sandwichRepository.findSandwichByName("Smos").getId();
            System.out.println("SMOS : " +smosID);
            UUID martinoID = sandwichRepository.findSandwichByName("Martino").getId();
            System.out.println("MARTINO : " +martinoID);
            UUID eiID = sandwichRepository.findSandwichByName("Ei en spek").getId();
            System.out.println("EI : " +eiID);


            recommendedItemRepository.save(RecommendedItem.RecommendItemBuilder.aRecommendedItem().withRecommendedItemEmail("0498157802").withRecommendedItemRatedItem(smosID).withRecommendedItemRating(4.0f).build());

            recommendedItemRepository.save(RecommendedItem.RecommendItemBuilder.aRecommendedItem().withRecommendedItemEmail("1").withRecommendedItemRatedItem(smosID).withRecommendedItemRating(5.0f).build());
            recommendedItemRepository.save(RecommendedItem.RecommendItemBuilder.aRecommendedItem().withRecommendedItemEmail("1").withRecommendedItemRatedItem(martinoID).withRecommendedItemRating(5.0f).build());
            recommendedItemRepository.save(RecommendedItem.RecommendItemBuilder.aRecommendedItem().withRecommendedItemEmail("1").withRecommendedItemRatedItem(eiID).withRecommendedItemRating(5.0f).build());


            recommendedItemRepository.save(RecommendedItem.RecommendItemBuilder.aRecommendedItem().withRecommendedItemEmail("2").withRecommendedItemRatedItem(smosID).withRecommendedItemRating(1.0f).build());
            recommendedItemRepository.save(RecommendedItem.RecommendItemBuilder.aRecommendedItem().withRecommendedItemEmail("2").withRecommendedItemRatedItem(martinoID).withRecommendedItemRating(5.0f).build());
            recommendedItemRepository.save(RecommendedItem.RecommendItemBuilder.aRecommendedItem().withRecommendedItemEmail("1").withRecommendedItemRatedItem(eiID).withRecommendedItemRating(4.0f).build());


        };
    }

    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**");
        }
    }

    private void saveUserMapToRepository(RecommendedItemRepository repository, HashMap<User, Map<Item, Float>> userMap) {
        List<RecommendedItem> recommendedItems = userMap.entrySet().stream()
                .flatMap(entry -> toRecommendedItems(entry))
                .collect(Collectors.toList());

        repository.saveAll(recommendedItems);
    }

    private Stream<RecommendedItem> toRecommendedItems(Map.Entry<User, Map<Item, Float>> entry) {
        List<RecommendedItem> recommendedItems = new ArrayList<>();
        for(Item recommendedItem : entry.getValue().keySet()) {
            RecommendedItem aRecommendedItem = new RecommendedItem();
            aRecommendedItem.setEmailAddress(entry.getKey().toString());
            aRecommendedItem.setRatedItem(UUID.fromString(recommendedItem.toString()));
            aRecommendedItem.setRating(entry.getValue().get(recommendedItem));
            recommendedItems.add(aRecommendedItem);
        }
        return recommendedItems.stream();
    }
}