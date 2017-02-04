package com.drivingschool.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@NamedQueries({
        @NamedQuery(name = "Appointment.findByInstructor",
                query = "SELECT a FROM Appointment a WHERE a.monitor.username = :username"),
        @NamedQuery(name = "Appointment.findBySchoolId",
                query = "SELECT a FROM Appointment a WHERE a.card.school.id = :schoolId"),
        @NamedQuery(name = "Appointment.findByAdmin",
                query = "SELECT a FROM Appointment a WHERE a.card.school.admin.username = :username"),
})
@Table(name = "appointments")
public class Appointment {

    @Id
    private String id;
    private Long start;
    private Long end;
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

    public Long getStart() {
        return start;
    }

    public void setStart(Long startingTime) {
        this.start = startingTime;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long endingTime) {
        this.end = endingTime;
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
