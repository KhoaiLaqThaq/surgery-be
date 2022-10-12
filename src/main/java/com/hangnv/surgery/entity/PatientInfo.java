package com.hangnv.surgery.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "patient_info")
public class PatientInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "f_name")
    private String fName;

    @Column(name = "f_phone")
    private String fPhone;

    @Column(name = "m_name")
    private String mName;

    @Column(name = "m_phone")
    private String mPhone;

    private String note;
}
