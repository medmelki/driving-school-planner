package com.drivingschool.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    private String id;
    private Long startingTime;
    private Double duration;
    @OneToOne
    private Card card;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(Long startingTime) {
        this.startingTime = startingTime;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
