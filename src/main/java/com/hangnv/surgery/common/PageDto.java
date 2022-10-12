package com.hangnv.surgery.common;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true)
public class PageDto extends BaseDto {
    @SuppressWarnings("rawtypes")
    private List content;
    private long totalElements;
    private int number;
    private int numberOfElements;
    private int totalPages;

    private boolean first;
    private boolean last;

}