package com.b127.dev.cvclassifier;

import com.b127.dev.cvclassifier.entity.enums.Skill;
import com.b127.dev.cvclassifier.util.eductionanalyzer.Analyzer;
import lombok.RequiredArgsConstructor;
import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class CvClassifierApplicationTests {

	@Test
	void contextLoads() {
		String edu = "[2017 – 2022]" +
				"Grade achieved: Degree : Bsc Engineering University of Ruhuna" +
				"2nd upper" +
				"[2013 – 2015]" +
				"Grade achieved: A/L" +
				"AAB";
		List<Integer> years = new ArrayList<>();
		Pattern p = Pattern.compile("20[0-2][0-9]");
		Matcher m = p.matcher(edu);
		while(m.find()) {
			years.add(Integer.parseInt(m.group()));
		}

		System.out.println(years.toString());
		for(int i = years.size() - 1; i > 0; i -= 2) {

			int yearGap = years.get(i) - years.get(i - 1);

			if(yearGap == 2 && edu.toLowerCase().contains("diploma")) {
				System.out.println("Diploma : 2 years");
			} else if ( yearGap > 3  && edu.toLowerCase().contains("degree")) {
				System.out.println("Degree : " + yearGap + " years");
			} else {
				System.out.println("Can't extract the education details");
			}
		}

		assertEquals(1, 1);
	}

}
