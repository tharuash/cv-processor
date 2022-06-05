package com.b127.dev.cvclassifier.util.resumeparser;

import com.b127.dev.cvclassifier.entity.Resume;
import com.b127.dev.cvclassifier.entity.enums.City;
import gate.*;
import gate.util.GateException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static gate.Utils.stringFor;

@Component
public class ResumeParser {

    public Resume parseUsingGateAndAnnie(String path) throws GateException, IOException {

        System.setProperty("gate.site.config", System.getProperty("user.dir") + "/GATEFiles/gate.xml");
        if (Gate.getGateHome() == null)
            Gate.setGateHome(new File(System.getProperty("user.dir") + "/GATEFiles"));
        if (Gate.getPluginsHome() == null)
            Gate.setPluginsHome(new File(System.getProperty("user.dir") + "/GATEFiles/plugins"));
        Gate.init();

        Annie annie = new Annie();
        annie.initAnnie();

        Resume resume;

        File fileToParse = new File(path);
        URL u = fileToParse.toURI().toURL();
        FeatureMap params = Factory.newFeatureMap();
        params.put("sourceUrl", u);
        params.put("preserveOriginalContent", Boolean.TRUE);
        params.put("collectRepositioningInfo", Boolean.TRUE);
        Corpus corpus = Factory.newCorpus("Annie corpus");
        Document resumeDocument = (Document) Factory.createResource("gate.corpora.DocumentImpl", params);
        try {
            corpus.add(resumeDocument);
            annie.setCorpus(corpus);
            annie.execute();

            resume = parseIntoResumeExpand(corpus, path);

        } finally {
            corpus.clear();
            Factory.deleteResource(resumeDocument);


        }
        return resume;

    }

    private Resume parseIntoResumeExpand(Corpus corpus, String path) {
        Iterator<Document> iter = corpus.iterator();
        Resume resume = null;
        if (iter.hasNext()) {
            Document doc = (Document) iter.next();
            AnnotationSet defaultAnnotSet = doc.getAnnotations();

            String education = parseSectionHeading("education_and_training", defaultAnnotSet, doc);
            if (education != null) education = education.trim();

            String experience = parseSectionHeading("work_experience", defaultAnnotSet, doc);
            if (experience != null) experience = experience.trim();

            String skills = parseSectionHeading("skills", defaultAnnotSet, doc);
            if (skills != null) skills = skills.trim();

            String s = readFile(path);

            City city = getLocationOfCandidate(s);

            String phone = getUsingRegexp(RegExPattern.PHONE.getValue(), s);
            if (phone == null) phone = parseAnnSectionSingleRes("PhoneFinder", defaultAnnotSet, doc);

            String email = getUsingRegexp(RegExPattern.EMAIL.getValue(), s);
            if (email == null) email = parseAnnSectionSingleRes("EmailFinder", defaultAnnotSet, doc);

            resume = new Resume(0L, null, email, path, city, phone, education, experience, skills, LocalDateTime.now());

        }
        return resume;
    }

    private String getUsingRegexp(String patterns, String s) {
        Pattern pattern = Pattern.compile(patterns);
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }


    private City getLocationOfCandidate(String s) {
        City city = null;
        if (s != null) {
            Optional<City> any = Arrays.stream(City.values())
                    .filter(eCity -> s.toLowerCase().contains(eCity.toString().toLowerCase()))
                    .findAny();
            if (any.isPresent()) {
                city = any.get();
            }
        }
        return city;
    }

    private List<Map<String, String>> parseSectionHeadingWithMultipleSubSections(String section, AnnotationSet defaultAnnotSet, Document doc) {
        AnnotationSet curAnnSet;
        Iterator it;
        Annotation currAnnot;

        curAnnSet = defaultAnnotSet.get(section);
        it = curAnnSet.iterator();
        List<Map<String, String>> sectionRes = new ArrayList<>();
        while (it.hasNext()) {
            Map<String, String> subSection = new HashMap<>();
            currAnnot = (Annotation) it.next();
            String key = (String) currAnnot.getFeatures().get("sectionHeading");
            String value = stringFor(doc, currAnnot);
            if (!StringUtils.isBlank(key) && !StringUtils.isBlank(value)) {
                subSection.put(key, value);
            }
            if (!subSection.isEmpty()) {
                sectionRes.add(subSection);
            }
        }
        return sectionRes;
    }

    private String parseSectionHeading(String section, AnnotationSet defaultAnnotSet, Document doc) {
        AnnotationSet curAnnSet;
        Iterator it;
        Annotation currAnnot;

        curAnnSet = defaultAnnotSet.get(section);
        it = curAnnSet.iterator();
        while (it.hasNext()) {
            currAnnot = (Annotation) it.next();
            String value = stringFor(doc, currAnnot);
            if (!StringUtils.isBlank(value)) {
                return value;
            }
        }
        return null;
    }

    private List<String> parseAnnSection(String annSection, AnnotationSet defaultAnnotSet, Document doc) {
        AnnotationSet curAnnSet;
        Iterator it;
        Annotation currAnnot;

        curAnnSet = defaultAnnotSet.get(annSection);
        it = curAnnSet.iterator();
        List<String> strings = new ArrayList<>();
        while (it.hasNext()) {
            currAnnot = (Annotation) it.next();
            String s = stringFor(doc, currAnnot);
            if (s != null && s.length() > 0) {
                strings.add(s);
            }
        }
        return strings;
    }

    private String parseAnnSectionSingleRes(String annSection, AnnotationSet defaultAnnotSet, Document doc) {
        AnnotationSet curAnnSet;
        Iterator it;
        Annotation currAnnot;

        curAnnSet = defaultAnnotSet.get(annSection);
        it = curAnnSet.iterator();
        while (it.hasNext()) {
            currAnnot = (Annotation) it.next();
            String s = stringFor(doc, currAnnot);
            if (s != null && s.length() > 0) {
                return s;
            }
        }
        return null;
    }

    private String readFile(String path) {
        File file = new File(path);
        String text = null;
        String extension = FilenameUtils.getExtension(file.getName());
        if (extension.equals("pdf")) {
            text = readPdf(path);
        } else if (extension.equals("doc") || extension.equals("docx")) {
            text = readDocxFile(path);
        }
        return text;
    }

    private String readPdf(String path) {
        String text = null;
        try {
            PDDocument document = PDDocument.load(new File(path));
            if (!document.isEncrypted()) {
                PDFTextStripper stripper = new PDFTextStripper();
                text = stripper.getText(document);
            }
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;

    }


    public String readDocxFile(String path) {
        String text = null;
        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());

            XWPFDocument document = new XWPFDocument(fis);
            List<XWPFParagraph> paragraphs = document.getParagraphs();

            StringBuilder stringBuilder = new StringBuilder();
            for (XWPFParagraph para : paragraphs) {
                stringBuilder.append(para.getText());
            }
            text = stringBuilder.toString();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }
}
