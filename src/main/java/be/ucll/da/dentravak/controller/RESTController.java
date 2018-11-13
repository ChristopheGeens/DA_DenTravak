package be.ucll.da.dentravak.controller;

import be.ucll.da.dentravak.model.SandwichOrder;
import be.ucll.da.dentravak.model.Sandwich;
import be.ucll.da.dentravak.repository.SandwichOrderRepository;
import be.ucll.da.dentravak.repository.SandwichRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class RESTController {

    @Autowired
    private SandwichOrderRepository sandwichOrderRepository;
    @Autowired
    private SandwichRepository sandwichRepository;

    public RESTController(SandwichOrderRepository sandwichOrderRepository, SandwichRepository sandwichRepository){
        this.sandwichOrderRepository = sandwichOrderRepository;
        this.sandwichRepository = sandwichRepository;
    }

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/orders")
    public List<SandwichOrder> getOrders() {
//        Sandwich sandwich = Sandwich.SandwichBuilder.aSandwich().withName("Smos").withIngredients(getIngredients()).withPrice(2.20).build();
//        SandwichOrder order = SandwichOrder.OrderBuilder.anOrder().withSandwiches(Arrays.asList(sandwich)).build();
//        List<OrderItem> items = new ArrayList<OrderItem>();
//        items.add(OrderItem.OrderItemBuilder.anOrderItem().withBreadType(BreadTypeEnum.TURKISHBREAD).withSandwichName("Martino").withQuantity(4).withPrice(new BigDecimal("12.80")).build());
//        items.add(OrderItem.OrderItemBuilder.anOrderItem().withBreadType(BreadTypeEnum.WRAP).withSandwichName("Smos").withQuantity(2).withPrice(new BigDecimal("6.40")).build());
//        SandwichOrder order = SandwichOrder.OrderBuilder.anOrder().withOrderItems(items).build();
//        return Arrays.asList(order);
        return (List<SandwichOrder>) sandwichOrderRepository.findAll();
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public void addOrder(@RequestBody SandwichOrder sandwichOrder){
        sandwichOrderRepository.save(sandwichOrder);
    }

    @RequestMapping("/ingredients")
    public List<String> getIngredients() {

//            return StreamSupport.stream(ingrdientRepository.findAll().spliterator(),false)
//                    .map(ingredient -> (ingredient.setName("")))
//                    .collect(Collectors.toList());


//        Ingredient sla = Ingredient.IngredientBuilder.anIngredient().withName("Sla").build();
//        Ingredient tomaat = Ingredient.IngredientBuilder.anIngredient().withName("Tomaat").build();
        return Arrays.asList(null);
    }

    @RequestMapping("/sandwiches")
    public List<Sandwich> getSandwiches() {
        return (List<Sandwich>) StreamSupport.stream(sandwichRepository.findAll().spliterator(),false)
                .collect(Collectors.toList());

    }

    @RequestMapping(value = "/sandwich", method = RequestMethod.POST)
    public Sandwich addSandwich(@RequestBody Sandwich sandwich) {
        sandwichRepository.save(sandwich);
        return sandwich;
    }

    @RequestMapping(value = "/sandwich/{id}", method = RequestMethod.GET)
    public Optional<Sandwich> showSandwich(@PathVariable UUID id) {
        return sandwichRepository.findById(id);
    }

    @RequestMapping(value = "/sandwich/{id}", method = RequestMethod.PUT)
    public Sandwich editSandwich(@PathVariable UUID id, @RequestBody Sandwich sandwich) {
        System.out.println("UPDATE method");
        if(id.equals(sandwich.getId())){
            System.out.println("ID matches");
            sandwichRepository.save(sandwich);
        }
        return sandwich;
    }

    @RequestMapping(value = "/sandwiches/{id}", method = RequestMethod.DELETE)
    public void deleteSandwich(@PathVariable UUID id) {
        System.out.println("DELETE method");
        sandwichRepository.deleteById(id);
    }


}
