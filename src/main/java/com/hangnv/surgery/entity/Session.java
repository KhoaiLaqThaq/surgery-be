package com.hangnv.surgery.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "session")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String diagnosis;
    private String symptom;

    @Column(name = "treatment_plan")
    private String treatmentPlan;
    private String note;
    private Integer status;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "next_time")
    private LocalDate nextTime;

    @Column(name = "total_price")
    private BigDecimal totalPrice;
    
    @Column(name = "free_cost_service")
    private Boolean freeCostService;
    
    // customer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = true)
    private Patient patient;

    // prescription
    @OneToMany(mappedBy = "session", fetch = FetchType.LAZY)
    private List<Prescription> prescriptions;

    @PrePersist
    private void prePersist() {
        setCreatedDate(LocalDateTime.now());
        setModifiedDate(LocalDateTime.now());
    }

    @PreUpdate
    private void preUpdate() {
        setModifiedDate(LocalDateTime.now());
    }
}
