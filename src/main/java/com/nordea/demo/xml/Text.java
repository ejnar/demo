package com.nordea.demo.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@XmlRootElement
public class Text {

    List<Sentence> sentences;
    int index = 1;
    int sumOfWord = 0;

    @XmlElement
    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentence(List<Sentence> sentences) {
        this.sentences = sentences;
    }

    public void setSentence(Sentence sentence) {
        if(sentences == null){
            sentences = new ArrayList<>();
        }
        sentences.add(sentence);
    }


    public void xmlBuilder() {
        StringBuilder builder = new StringBuilder();
        sentences.stream().forEach(s -> {
            builder.append("<sentence>").append(s.xmlBuilder()).append("</sentence>").append("\n");
            sumOfWord = sumOfWord + s.getWord().size();
        });
        sentences = new ArrayList<>();
        if(builder.length() > 0)
            System.out.println(builder);
    }

    public void countWord() {
        StringBuilder builder = new StringBuilder();
        for(int i = 1; i <= sumOfWord; i++){
            builder.append("Word ").append(i).append(", ");
        }
        System.out.println(builder);
    }


    public void cvsBuilder() {
        StringBuilder builder = new StringBuilder();
        sentences.stream().forEach(s -> {
            builder.append(s.cvsBuilder(index++)).append("\n");
        });
        sentences = new ArrayList<>();
        if(builder.length() > 0)
            System.out.println(builder);
    }

}
