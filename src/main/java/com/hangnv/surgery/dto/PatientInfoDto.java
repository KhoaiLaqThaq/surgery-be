package com.hangnv.surgery.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PatientInfoDto {

    private Long id;
    private String fName;
    private String fPhone;
    private String mName;
    private String mPhone;
    private String note;

}
