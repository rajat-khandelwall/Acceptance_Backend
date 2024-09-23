package com.vw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "remainingData")
public class RemainingData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    
    private String agrmntNumber;

    private double srvcRemainBdgt;

    private double miscRemainBdgt;

    private double totalRemainBdgt;
    

}