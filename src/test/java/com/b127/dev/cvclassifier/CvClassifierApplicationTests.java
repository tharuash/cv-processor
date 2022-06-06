package com.b127.dev.cvclassifier;

import com.b127.dev.cvclassifier.entity.enums.Skill;
import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class CvClassifierApplicationTests {

	@Test
	void contextLoads() {
		String skillsString = "JavaSpring / Spring BootSQLNodeJSDesign PatternsAlgorithms";

		List<String> keywords = Arrays.stream(Skill.values()).map(Skill::toString).collect(Collectors.toList());
		Trie trie = Trie.builder().addKeywords(keywords).build();

		List<String> emits = trie.parseText(skillsString).stream().map(Emit::getKeyword).distinct().collect(Collectors.toList());
		emits.forEach(System.out::println);

		assertEquals(1, 1);
	}

}
