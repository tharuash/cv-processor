package com.b127.dev.cvclassifier;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class CvClassifierApplicationTests {

	@Test
	void contextLoads() {
		LocalDateTime testTime = LocalDateTime.now();
		String s =  testTime.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)) + " on "  + testTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
		assertEquals("11:10 PM on June 5, 2022",s);
	}

}
