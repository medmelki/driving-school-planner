package com.drivingschool.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@NamedQueries({
        @NamedQuery(name = "Appointment.findByMonitor",
                query = "SELECT a FROM Appointment a, User u WHERE a.monitor.username = u.username"),
//        @NamedQuery(name = "Appointment.findByName",
//                query = "SELECT a FROM Appointment a WHERE a.name = :name"),
})
@Table(name = "appointments")
public class Appointment {

    @Id
    private String id;
    private Long startingTime;
    private Long endingTime;
    @OneToOne
    private Card card;
    @OneToOne
    private User monitor;

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

    public Long getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(Long endingTime) {
        this.endingTime = endingTime;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public User getMonitor() {
        return monitor;
    }

    public void setMonitor(User monitor) {
        this.monitor = monitor;
    }
}
