package com.github.phh.benefit.common.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p> TODO
 *
 * @author phh
 * @version V1.0
 * @date 2019/2/25
 */
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BeanB {
    private Boolean open;
    private String name;
    private int age;
    private Double height;
    private Date birthday;
    private int sex;
    private String other;
    private LocalDate localDate;
    private BeanA.Op op;
    private LocalDateTime localDateTime;
    private LocalDateTime localDateTime2;
}
