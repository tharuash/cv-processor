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
		String patterns = "^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$";

		Pattern pattern = Pattern.compile(patterns);
		Matcher matcher = pattern.matcher(".f@sliit.lkEXTRACURRICULARACTIVITIESParticipatedtoInterSchooldivisionalchesscompetitionin2009MemberofSchoolChessTeamVishakaGirls'HighSchool,Badulla\n" +
				"PRASADINIRATHNAYAKEUNDERGRADUATEEXECUTIVESUMMARYHighlymotivatedundergraduatewithastrongfoundationinprogrammingprinciplesandaproficientinavarietyofplatforms,languages,withaninnateabilitytolearnandmasternewtechnologiesSKILLSJavaJavaScriptHTML/CSSPythonMySQLHOWTOCONTACTMEAddress:No111,Bokanoruwa,Galauda,HaliEla,BadullaPhone:+94705888539Email:rathnayakeprasadini@gmail");
		if (matcher.find()) {
			System.out.println(matcher.group(0));
		} else {
			System.out.println("No match");
		}

		assertEquals(1, 1);
	}

}
