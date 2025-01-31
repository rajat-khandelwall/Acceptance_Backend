package com.vw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "levelInfo")
@Data
public class LevelInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int levelId;
    private int level;
    private int member;
    private String location;
    private double price;

}
