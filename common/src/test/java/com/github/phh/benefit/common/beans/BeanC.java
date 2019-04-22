package com.github.phh.benefit.common.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

/**
 * <p> TODO
 *
 * @author phh
 * @version V1.0
 * @project: spring
 * @package com.phh.test.cglib
 * @date 2019/2/25
 */
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BeanC {
    private Boolean open;
    private String name;
    private int age;
    private Double height;
    private Date birthday;
    private LocalDate localDate;
}
