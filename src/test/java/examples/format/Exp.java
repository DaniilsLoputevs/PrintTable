package examples.format;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.time.*;
import java.util.Calendar;

public class Exp {
    @Test public void exp1() {
//        ArrayList<String> list1 = new ArrayList<>();
//        List<String> list2 = new ArrayList<>();
//        System.out.println(list1);
//        System.out.println(list2);
        
        
        
        // java.sql.Date
        Date sqlDate = new Date(System.currentTimeMillis());
        System.out.println("java.sql.Date: " + sqlDate);
        // java.sql.Date: 2024-11-14
        
        // java.util.Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.OCTOBER, 13, 12, 30, 45);
        System.out.println("java.util.Calendar: " + calendar.getTime());
        // java.util.Calendar: Sun Oct 13 12:30:45 EEST 2024
        
        // java.time.ZonedDateTime
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2024, 10, 13, 12, 30, 45, 0, ZoneId.of("America/New_York"));
        System.out.println("java.time.ZonedDateTime: " + zonedDateTime);
        // java.time.ZonedDateTime: 2024-10-13T12:30:45-04:00[America/New_York]
        
        // java.time.OffsetDateTime
        OffsetDateTime offsetDateTime = OffsetDateTime.of(2024, 10, 13, 12, 30, 45, 0, ZoneOffset.ofHours(-4));
        System.out.println("java.time.OffsetDateTime: " + offsetDateTime);
        // java.time.OffsetDateTime: 2024-10-13T12:30:45-04:00
        
        // java.time.OffsetTime
        OffsetTime offsetTime = OffsetTime.of(12, 30, 45, 0, ZoneOffset.ofHours(-4));
        System.out.println("java.time.OffsetTime: " + offsetTime);
        // java.time.OffsetTime: 12:30:45-04:00
        
        // java.time.Instant
        Instant instant = Instant.now();
        System.out.println("java.time.Instant: " + instant);
        // java.time.Instant: 2024-11-14T16:24:15.117070Z
        
        // java.time.Period
        Period period = Period.of(1, 2, 3); // 1 год, 2 месяца и 3 дня
        System.out.println("java.time.Period: " + period.toString());
        // java.time.Period: P1Y2M3D
        
        // java.time.Duration
        Duration duration = Duration.ofHours(5).plusMinutes(30); // 5 часов и 30 минут
        System.out.println("java.time.Duration: " + duration);
        // java.time.Duration: PT5H30M
        
        
        System.out.println("===========");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(calendar);
        System.out.println("Formatted Calendar: " + formattedDate);
    }
}
