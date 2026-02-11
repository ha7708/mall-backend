package org.zerock.mallapi.controller.formatter;

import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

// 날짜/시간 주의
// 브라우저 : 문자열로 전송
// 서버 : LocalDate 또는 LocalDateTime 으로 처리함
// 그러므로 이를 자동으로 변환해주는 이 클래스를 정의한다.
// 이후 스프링 MVC 동작 과정에서 사용될 수 있도록 설정을 추가해주어야 함.
public class LocalDateFormatter implements Formatter<LocalDate> {
    @Override
    public LocalDate parse(String text, Locale locale) {
        return LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(object);
    }

}
