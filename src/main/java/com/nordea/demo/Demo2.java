package com.nordea.demo;

import com.nordea.demo.xml.Sentence;
import com.nordea.demo.xml.Text;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Demo2 {

    public static void main(String[] args) {

        try {
            new Demo2().read("large.in");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void read (String path) throws IOException {

        String tmp = "";
        Text text = new Text();
        List<String> lineSentence = new ArrayList<>();

        Stream<String> lines = Files.lines(Paths.get(path));
        lines.forEach(line -> {
            //System.out.println(l);
            stream(line, tmp, lineSentence, text);
        });

        generate(text);
    }

    void stream(String line, String tmp, List<String> lineSentence, Text text){

        if(tmp.length() > 0) {
            line = tmp + " " + line;
            tmp = "";
        }
        while(true) {
            int i = line.indexOf(".");
            if(i < 0) {
                tmp = line;
                break;
            } else {
                lineSentence.add(line.substring(0, i).trim());
                line = line.substring(i + 1, line.length());
            }
        }
        for (String _sentence: lineSentence) {
            spliteToSentence(_sentence, text);
        }
        lineSentence.clear();
    }

    void spliteToSentence(String _sentence, Text text){
        Sentence sentence = new Sentence();
        String[] words = _sentence.split(" ");
        for (String word: words){
            sentence.setWord(word.trim());
        }
        //Collections.sort(sentence.getWord(), String.CASE_INSENSITIVE_ORDER);

        //List<String> sortedNames = sentence.getWord().stream().sorted().collect(Collectors.toList());
        //sentence.setWord(sortedNames);
        text.setSentence(sentence);
    }

    void generate(Text text){

        System.out.println(text.xmlBuilder());

        System.out.println(text.cvsBuilder());

    }

    void generateJaxb(Text text){
        try {
            JAXBContext contextObj = JAXBContext.newInstance(Text.class);
            Marshaller marshallerObj = contextObj.createMarshaller();
            //marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshallerObj.marshal(text, System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
