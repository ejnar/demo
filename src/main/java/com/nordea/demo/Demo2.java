package com.nordea.demo;

import com.nordea.demo.xml.Sentence;
import com.nordea.demo.xml.Text;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;
// -Xmx1024m
public class Demo2 {

    final Text text = new Text();
    String tmp = "";

    public static void main(String[] args) {

        try {
            new Demo2().print("large.in");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void print (String path) throws IOException {

        printoutXML(text, path);

        printoutCVS(text, path);
    }

    void stream(String line, String tmp, Text text){

        List<String> lineSentence = new ArrayList<>();

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
        text.setSentence(sentence);
    }

    void printoutXML(Text text, String path) throws IOException{

        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>").append("\n");
        builder.append("<text>").append("\n");
        System.out.println(builder);

        try (BufferedReader fileBufferReader = new BufferedReader(new FileReader(path), 1000 * 8192)) {
            String line;
            while ((line = fileBufferReader.readLine()) != null) {
                stream(line, tmp, text);
                text.xmlBuilder();
            }
        }
        System.out.println("</text>");
    }

    void printoutCVS(Text text, String path) throws IOException{

        StringBuilder builder = new StringBuilder();

        //text.countWord();
        try (BufferedReader fileBufferReader = new BufferedReader(new FileReader(path), 1000 * 8192)) {
            String line;
            while ((line = fileBufferReader.readLine()) != null) {
                stream(line, tmp, text);
                text.cvsBuilder();
            }
        }

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
