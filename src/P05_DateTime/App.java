package P05_DateTime;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.TemporalField;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class App {

    public static void main(String[] args) throws InterruptedException {
        /* 기존에 제공되던 Date 클래스 */
        Date date = new Date(); // 이름과 달리 시간까지도 표현가능(실상 TimeStamp에 가까움)
        long time = date.getTime(); // 사람용 시간이 아닌 기계용 시간(EPOCK)

        Thread.sleep(1000 * 3);
        Date after3Seconds = new Date();
        System.out.println(after3Seconds);
        after3Seconds.setTime(time);
        System.out.println(after3Seconds); // 이전 값으로 돌아가버림(mutable)

        /* Java 8부터 제공되는 API */

        /**
         * Instant
         */

        // human time
        Instant instant = Instant.now(); // 현재 UTC(GMT)를 반환
        System.out.println(instant);
        System.out.println(instant.atZone(ZoneId.of("UTC")));

        // human time of zone
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime now = instant.atZone(zoneId);
        System.out.println("Zone : " + zoneId);
        System.out.println("localDateTime : " + now);

        // epoch time
        Instant epoch = Instant.EPOCH;
        System.out.println(epoch);

        /**
         * ZonedDateTime
         * ZoneId 혹은 ZoneOffset
         */

        // ZoneId
        System.out.println("Current(UTC) " + ZonedDateTime.now());
        System.out.println("Current(시카고) " + ZonedDateTime.now(ZoneId.of("America/Chicago")));
        System.out.println("Current(파리) " + ZonedDateTime.now(ZoneId.of("Europe/Paris")));

        // ZoneId.of()의 인자로 ZoneOffset을 넣는 경우에도 적용된다.
        System.out.println("Current(+2) " + ZonedDateTime.now(ZoneId.of("+2")));
        System.out.println("Current(UTC+2) " + ZonedDateTime.now(ZoneId.of("UTC+2")));
        System.out.println("Current(UTC+02) " + ZonedDateTime.now(ZoneId.of("UTC+02")));
        System.out.println("Current(UTC+02:00) " + ZonedDateTime.now(ZoneId.of("UTC+02:00")));

        System.out.println("Current(GMT+2) " + ZonedDateTime.now(ZoneId.of("GMT+2")));
        System.out.println("Current(GMT+02) " + ZonedDateTime.now(ZoneId.of("GMT+02")));
        System.out.println("Current(GMT+02:00) " + ZonedDateTime.now(ZoneId.of("GMT+02:00")));

        // ZoneOffset
        System.out.println("Current(+2) " + ZonedDateTime.now(ZoneOffset.of("+02")));




        /**
         * LocalDateTime
         */

        LocalDateTime nowLocalDateTime = LocalDateTime.now();

        // atZone() 사용
        ZonedDateTime nowInSeoul = nowLocalDateTime.atZone(ZoneId.of("Asia/Seoul"));
        System.out.println("now(no timezone) " + nowLocalDateTime);
        System.out.println("now(with timezone) " + nowInSeoul);
        System.out.println("now(Paris) " + ZonedDateTime.ofInstant(nowInSeoul.toInstant(), ZoneId.of("Europe/Paris")));

        // of() 사용
        ZonedDateTime nowInSeoul2 = ZonedDateTime.of(nowLocalDateTime, ZoneId.of("Asia/Seoul"));
        System.out.println("now(no timezone) " + nowLocalDateTime);
        System.out.println("now(with timezone) " + nowInSeoul2);

        // of()로 특정 DateTime구하기
        LocalDateTime birthDay = LocalDateTime.of(1995, Month.FEBRUARY, 14, 0, 0);
        System.out.println("birthDay " + birthDay);

        Instant instant1 = Instant.now();
        ZonedDateTime zonedDateTime = instant1.atZone(ZoneId.of("Asia/Seoul"));
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        System.out.println("instant " + instant1);
        System.out.println("zoned date time " + zonedDateTime);
        System.out.println("local date time " + localDateTime);

    }
}
