package com.hangnv.surgery.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "material_batch")
public class MaterialBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String unit;
    private Integer amount;
    @Column(name = "receipt_date")
    private LocalDate receiptDate;
    @Column(name = "expired_date")
    private LocalDate expiredDate;
    private BigDecimal price;
    private BigDecimal sales;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "material_id", referencedColumnName = "id", nullable = false)
    private Material material;
    
    @PrePersist
    public void prePersist() {
    	setReceiptDate(LocalDate.now());
    	setCreatedDate(LocalDateTime.now());
    	setModifiedDate(LocalDateTime.now());
    }
    
    @PreUpdate
    public void preUpdate() {
    	setModifiedDate(LocalDateTime.now());
    }
}
