package be.ucll.da.dentravak.controller;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class RecommendedItem {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    private String emailAddress;
    private UUID ratedItem;
    private float rating;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public UUID getRatedItem() {
        return ratedItem;
    }

    public void setRatedItem(UUID ratedItem) {
        this.ratedItem = ratedItem;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }


    public static class RecommendItemBuilder{

        private String emailAddress;
        private UUID ratedItem;
        private float rating;

        private RecommendItemBuilder(){}

        public static RecommendItemBuilder aRecommendedItem(){
            return new RecommendItemBuilder();
        }

        public RecommendItemBuilder withRecommendedItemEmail(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public RecommendItemBuilder withRecommendedItemRatedItem(UUID ratedItem) {
            this.ratedItem = ratedItem;
            return this;
        }

        public RecommendItemBuilder withRecommendedItemRating(float rating) {
            this.rating = rating;
            return this;
        }

        //build
        public RecommendedItem build(){
            RecommendedItem recommendedItem = new RecommendedItem();

            recommendedItem.emailAddress = this.emailAddress;
            recommendedItem.ratedItem = this.ratedItem;
            recommendedItem.rating = this.rating;

            return recommendedItem;
        }
    }
}
