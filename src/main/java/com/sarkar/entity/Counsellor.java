package com.sarkar.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "counsellor")
public class Counsellor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer counsellorId;

    private String name;

    @Column(unique = true)
    private String email;

    private String pwd;

    // Updated to String to better represent a phone number
    private String phonNo;

    @CreationTimestamp
    private LocalDate createdDate;  // Updated to camelCase

    @UpdateTimestamp
    private LocalDate updatedDate;  // Updated to camelCase

    // Getters and setters
    public Integer getCounsellorId() {
        return counsellorId;
    }

    public void setCounsellorId(Integer counsellorId) {
        this.counsellorId = counsellorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPhonNo() {  // Updated return type to String
        return phonNo;
    }

    public void setPhonNo(String phonNo) {  // Updated parameter type to String
        this.phonNo = phonNo;
    }

    public LocalDate getCreatedDate() {  // Updated to camelCase
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {  // Updated to camelCase
        this.createdDate = createdDate;
    }

    public LocalDate getUpdatedDate() {  // Updated to camelCase
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {  // Updated to camelCase
        this.updatedDate = updatedDate;
    }
}
