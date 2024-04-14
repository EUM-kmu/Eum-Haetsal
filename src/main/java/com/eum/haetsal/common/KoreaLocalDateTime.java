package com.eum.haetsal.common;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
@Component
public class KoreaLocalDateTime {
    public static String localDateTimeToKoreaZoned(LocalDateTime dateTime){
        LocalDateTime dateTimeUTC = LocalDateTime.parse(dateTime.toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        ZonedDateTime koreaZoneTime = dateTimeUTC.atZone(ZoneId.of("Asia/Seoul"));
        String formattedKoreaZoneTime = koreaZoneTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ"));
        return formattedKoreaZoneTime;
    }
    public static String dateToKoreaZone(Date date){
        Instant instant = date.toInstant();
        LocalDateTime dateTimeUTC =  instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        ZonedDateTime koreaZoneTime = dateTimeUTC.atZone(ZoneId.of("Asia/Seoul"));
        String formattedKoreaZoneTime = koreaZoneTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ"));
        return formattedKoreaZoneTime;
    }
    public static LocalDate stringToLocalDateTime(String date)  {
        LocalDate formDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
        return formDate;
    }


}
