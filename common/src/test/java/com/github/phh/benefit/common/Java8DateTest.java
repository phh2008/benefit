package com.github.phh.benefit.common;

import com.github.phh.benefit.common.utils.LocalDateUtils;
import org.junit.Test;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * <p> TODO
 *
 * @author phh
 * @version V1.0
 * @project: ufa_b2
 * @package PACKAGE_NAME
 * @date 2018/7/27
 */
public class Java8DateTest {


    @Test
    public void test1() {
        System.out.println(LocalDate.now().minusDays(6).format(LocalDateUtils.FMT_YYYY_MM_DD));
    }

    @Test
    public void test2() {
        String tradeDateStart = "2018-07-21";
        String tradeDateEnd = "2018-07-27";
        long diff = LocalDate.parse(tradeDateStart, LocalDateUtils.FMT_YYYY_MM_DD).until(LocalDate.parse(tradeDateEnd, LocalDateUtils.FMT_YYYY_MM_DD), ChronoUnit.DAYS);
        System.out.println("diff:" + diff);
    }

    @Test
    public void test3() {
        LocalDate date = null;
        Date var = LocalDateUtils.toDate(date);
        System.out.println(var);
        LocalDateTime date2 = LocalDateTime.now();
        Date var2 = LocalDateUtils.toDate(date2);
        System.out.println(var2);
    }


    @Test
    public void test4() {
        LocalDateTime today = LocalDateTime.now();
        System.out.println(today.toLocalTime().format(LocalDateUtils.FMT_24HH_MM));
        System.out.println(today.format(LocalDateUtils.FMT_24HH_MM));
        System.out.println(today.toLocalDate().isEqual(LocalDate.now()));
        System.out.println(today.toLocalDate().format(LocalDateUtils.FMT_YY_LMM_LDD));
        System.out.println(today.format(LocalDateUtils.FMT_YY_LMM_LDD));

    }

    @Test
    public void test5() {
        Date d1 = LocalDateUtils.toDate(LocalDate.of(2018, 8, 15));
        long curr = Instant.now().toEpochMilli();
        double years = (curr - d1.getTime()) / (1000 * 60 * 60 * 24 * 1.0) / 365;
        System.out.println(String.valueOf(years));
        DecimalFormat fmt = new DecimalFormat("#0.#");
        fmt.setRoundingMode(RoundingMode.FLOOR);
        System.out.println(fmt.format(years));
    }

    @Test
    public void test6() {
        LocalDate end = LocalDate.of(2018, 10, 6);
        LocalDate start = LocalDate.now();
        System.out.println("between：" + ChronoUnit.DAYS.between(start, end));
        System.out.println("between：" + start.until(end, ChronoUnit.DAYS));
        System.out.println("---------------------------------------");
        System.out.println("between：" + ChronoUnit.DAYS.between(end, start));
        System.out.println("between：" + end.until(start, ChronoUnit.DAYS));
        System.out.println("---------------------------------------");
        System.out.println("between：" + start.until(start, ChronoUnit.DAYS));
    }

    @Test
    public void test7() {
        LocalDate date = LocalDate.of(2018, 4, 5);
        LocalTime time = LocalTime.of(14, 53);
        LocalDateTime before = LocalDateTime.of(date, time);
        LocalDateTime now = LocalDateTime.now();

        Duration duration = Duration.between(before, now);
        System.out.println(duration.getSeconds());

        System.out.println("----------------------------");
        long temp = before.until(now, ChronoUnit.SECONDS);
        System.out.println("SECONDS:" + temp);
        temp = before.until(now, ChronoUnit.MINUTES);
        System.out.println("MINUTES:" + temp);
        temp = before.until(now, ChronoUnit.HOURS);
        System.out.println("HOURS:" + temp);
        System.out.println("------------------------------");

        System.out.println(LocalDateUtils.fmtHuman(before));
    }

}
