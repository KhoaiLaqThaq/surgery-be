package com.hangnv.surgery.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "material")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String name;
    private String composition;
    private String suggest;
    private Integer total;
    private String unit;
    private BigDecimal price;
    private BigDecimal sales;

    //material type
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_type_id", referencedColumnName = "id", nullable = true)
    private MaterialType materialType;

    // material batch
    @OneToMany(mappedBy = "material", fetch = FetchType.LAZY)
    private List<MaterialBatch> materialBatches;

    // prescription
    @OneToMany(mappedBy = "material", fetch = FetchType.LAZY)
    private List<Prescription> prescriptions;

}
