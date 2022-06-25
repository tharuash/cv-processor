package com.b127.dev.cvclassifier.util.eductionanalyzer;

import com.b127.dev.cvclassifier.dto.AnalyzedEducationDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Analyzer {

    public List<AnalyzedEducationDTO> analyzeEducation(String educationDetails) {

        List<AnalyzedEducationDTO> analyzedEducationDTOS = new ArrayList<>();
        List<Integer> years = new ArrayList<>();

        Pattern p = Pattern.compile("20[0-2][0-9]");
        Matcher m = p.matcher(educationDetails);
        while(m.find()) {
            years.add(Integer.parseInt(m.group()));
        }

        for(int i = years.size() - 1; i > 0; i -= 2) {

            int yearGap = years.get(i) - years.get(i - 1);

            if(yearGap == 2 && educationDetails.toLowerCase().contains("diploma")) {
                analyzedEducationDTOS.add(new AnalyzedEducationDTO("Diploma", 2));
            } else if ( yearGap > 3  && educationDetails.toLowerCase().contains("degree")) {
                analyzedEducationDTOS.add(new AnalyzedEducationDTO("Degree", yearGap));
            } else {
                System.out.println("Can't extract the education details");
            }
        }

        return analyzedEducationDTOS;
    }
}
