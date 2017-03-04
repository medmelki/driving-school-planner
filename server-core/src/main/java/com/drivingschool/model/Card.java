package com.drivingschool.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "cards")
public class Card implements Serializable {

    @Id
    private String contractId;
    private String firstname;
    private String lastname;
    private String CIN;
    private String type;
    private String telephone;
    private String address;
    private String app;
    private String city;
    private String cp;
    private Long dateOfBirth;
    private Long dateOfInscription;
    private String email;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "card", orphanRemoval = true)
    private List<Appointment> appointments;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHOOL_ID")
    private School school;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public Long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Long getDateOfInscription() {
        return dateOfInscription;
    }

    public void setDateOfInscription(Long dateOfInscription) {
        this.dateOfInscription = dateOfInscription;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @JsonIgnore
    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
