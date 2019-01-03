package be.ucll.da.dentravak.model;

import be.ucll.da.dentravak.repository.LocalDateTimeConverter;
//import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class SandwichOrder {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    private UUID sandwichId;
    private String name;
    private BreadTypeEnum breadType;
    private BigDecimal price;
    private String mobilePhoneNumber;

    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime creationDate;

    public SandwichOrder(){
        this.creationDate = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getSandwichId() {
        return sandwichId;
    }

    public void setSandwichId(UUID sandwichId) {
        this.sandwichId = sandwichId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BreadTypeEnum getBreadType() {
        return breadType;
    }

    public void setBreadType(BreadTypeEnum breadType) {
        this.breadType = breadType;
    }

    public String getMobilePhoneNumber() {return mobilePhoneNumber;}

    public void setMobilePhoneNumber(String mobilePhoneNumber) {this.mobilePhoneNumber = mobilePhoneNumber;}

    public LocalDateTime getCreationDate() {return creationDate;}

    public void setCreationDate(LocalDateTime creationDate) {this.creationDate = creationDate;}

    public static class SandwichOrderBuilder{
        
        private String name;
        private BreadTypeEnum breadType;
        private String mobilePhoneNumber;
        private LocalDateTime creationDate;

        private SandwichOrderBuilder(){}

        public static SandwichOrderBuilder anOrder(){
            return new SandwichOrderBuilder();
        }

        public SandwichOrderBuilder withSandwichName(String name) {
            this.name = name;
            return this;
        }

        public SandwichOrderBuilder withCreationDate() {
            this.creationDate = LocalDateTime.now();
            return this;
        }

        public SandwichOrderBuilder withCreationDate(LocalDateTime date) {
            this.creationDate = date;
            return this;
        }

        public SandwichOrderBuilder withBreadType(BreadTypeEnum breadType) {
            this.breadType = breadType;
            return this;
        }

        public SandwichOrderBuilder withMobilePhoneNumber(String number) {
            this.mobilePhoneNumber = number;
            return this;
        }

        //build
        public SandwichOrder build(){
            SandwichOrder sandwichOrder = new SandwichOrder();

            sandwichOrder.name = this.name;
            sandwichOrder.breadType = this.breadType;
            sandwichOrder.mobilePhoneNumber = this.mobilePhoneNumber;
            sandwichOrder.creationDate = this.creationDate;

            return sandwichOrder;
        }
    }
}
