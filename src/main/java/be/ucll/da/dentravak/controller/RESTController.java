package be.ucll.da.dentravak.controller;

import be.ucll.da.dentravak.model.Sandwich;
import be.ucll.da.dentravak.model.SandwichOrder;
import be.ucll.da.dentravak.repository.SandwichOrderRepository;
import be.ucll.da.dentravak.repository.SandwichRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;




@RestController
public class RESTController {


    @Inject
    private DiscoveryClient discoveryClient;

    @Inject
    private SandwichRepository repository;

    @Inject
    private RestTemplate restTemplate;

    @Autowired
    private SandwichOrderRepository sandwichOrderRepository;
    @Autowired
    private SandwichRepository sandwichRepository;

    public RESTController(SandwichOrderRepository sandwichOrderRepository, SandwichRepository sandwichRepository){
        this.sandwichOrderRepository = sandwichOrderRepository;
        this.sandwichRepository = sandwichRepository;
    }

    @RequestMapping("/orders")
    public List<SandwichOrder> getOrders() {
        return (List<SandwichOrder>) sandwichOrderRepository.findAll();
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public SandwichOrder addSandwichOrder(@RequestBody SandwichOrder sandwichOrder){
        SandwichOrder order = new SandwichOrder();
        //order.setCreationDate(LocalDateTime.now());
        order.setBreadType(sandwichOrder.getBreadType());
        order.setName(sandwichOrder.getName());
        order.setMobilePhoneNumber(sandwichOrder.getMobilePhoneNumber());
        order.setSandwichId(sandwichOrder.getSandwichId());
        sandwichOrderRepository.save(order);
        return order;
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public Optional<SandwichOrder> showSandwichOrder(@PathVariable UUID id){
        return sandwichOrderRepository.findById(id);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.DELETE)
    public void deleteSandwichOrder(@PathVariable UUID id){
        sandwichOrderRepository.deleteById(id);
    }


    @RequestMapping("/sandwiches")
    public List<Sandwich> getSandwiches() {
        return (List<Sandwich>) StreamSupport.stream(sandwichRepository.findAll().spliterator(),false)
                .collect(Collectors.toList());

    }

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/sandwiches", method = RequestMethod.POST)
    public Sandwich addSandwich(@RequestBody Sandwich sandwich) {
        sandwichRepository.save(sandwich);
        return sandwich;
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/sandwiches/{id}", method = RequestMethod.GET)
    public Optional<Sandwich> showSandwich(@PathVariable UUID id) {
        return sandwichRepository.findById(id);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/sandwiches/{id}", method = RequestMethod.PUT)
    public Sandwich editSandwich(@PathVariable UUID id, @RequestBody Sandwich sandwich) {
        System.out.println(id + "/" + "NAME=" + sandwich.getName() + " ID=" + sandwich.getId() + " INGR=" + sandwich.getIngredients());
        if(id.equals(sandwich.getId())){
            sandwichRepository.save(sandwich);
        }
        return sandwich;
    }

    @RequestMapping(value = "/sandwiches/{id}", method = RequestMethod.DELETE)
    public void deleteSandwich(@PathVariable UUID id) {
        sandwichRepository.deleteById(id);
    }


//    @CrossOrigin(origins = "http://localhost:8081")
//    @RequestMapping(value = "/sandwichesByRecommended", method = RequestMethod.GET)
//    public List<Sandwich> sandwichesByRecommendation() {
//
//
//        List<RecommendedItem> recommendedItemsByEmailAddress = repository.findAllByEmailAddress("0498157802");
//        Map<Item, Float> userPrefences = mapToSlopeOneInput(recommendedItemsByEmailAddress);
//
//        SlopeOne slopeOnePredictionMachine = getSlopeOnePredictionMachine();
//        Map<Item,Float> ratings = slopeOnePredictionMachine.predict(userPrefences);
//
//        System.out.println(ratings);
//
//        List<Map.Entry> entries = new ArrayList<>();
//        for (Map.Entry<Item,Float> entry : ratings.entrySet()){
//            entries.add(entry);
//        }
//
//        entries.sort(Comparator.comparing(Map.Entry<Item, Float>::getValue));
//
//        System.out.println(entries);
//
//        List<Sandwich> sandwichesByRatings = new ArrayList<>();
//
//        for (Map.Entry<Item,Float> entry: entries) {
//            sandwichesByRatings.add(sandwichRepository.findById(UUID.fromString(entry.getKey().toString())).get());
//        }
//
//
////        List<Item> items = new ArrayList(ratings.keySet());
////        items.forEach(System.out::println);
////
////        List<Float> itemRatings = new ArrayList(ratings.values());
////        itemRatings.forEach(System.out::println);
//
//
//
//        Collections.reverse(sandwichesByRatings);
//
//        return sandwichesByRatings;
//    }
}
