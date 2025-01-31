package com.vw.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity(name = "projectDetails") 
public class ProjectDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer projectId;

    private String agrmntNumber;

    private String brand;
 
    private String department;

    private String subDeprtmt;

    private String projectName;

    private String generatedDate;

    private String fromDate;

    private String toDate;

    private String ordrNumber;

    private String respPersonal;

    private String srvcProvider;

    private String srvcReceiver;

    private String prjctDesc;

    private double srvcCost;

    private double srvcMonthlyCost;

    private double srvcRemainBdgt;

    private double miscCost;

    private double miscMonthlyBdgt;

    private double miscRemainBdgt;

    private double totalCost;

    private double totalMonthlyBdgt;

    private double totalRemainBdgt;

    private double miscPricing;

    private String mngrName;

    private String clientName;
     
    private String justification;
    
    private String location;
    
    @OneToMany(targetEntity = LevelInfo.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id", referencedColumnName = "projectId")
    private Set<LevelInfo> levelInfo;
    
    @OneToOne(targetEntity = CostDetails.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id", referencedColumnName = "projectId")
    private CostDetails costDetails;
    
    @OneToOne(targetEntity = RemainingData.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id", referencedColumnName = "projectId")
    private RemainingData remainingData;

}