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


    public StringBuilder xmlBuilder() {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>").append("\n");
        builder.append("<text>").append("\n");
        sentences.stream().forEach(s -> {
            builder.append("<sentence>").append(s.xmlBuilder()).append("</sentence>").append("\n");
        });
        builder.append("</text>").append("\n");
        return builder;
    }

    public StringBuilder cvsBuilder() {
        AtomicInteger sumOfWord = new AtomicInteger();
        StringBuilder builder = new StringBuilder();

        builder.append("\n");
        sentences.stream().forEach(s -> {
            sumOfWord.set(sumOfWord.get() + s.word.size());
            builder.append(s.cvsBuilder(index++)).append("\n");
        });

        StringBuilder tmp = new StringBuilder();
        for(int i = 1; i <= sumOfWord.get(); i++){
            tmp.append("Word ").append(i).append(", ");

        }
        builder.insert(0, tmp);

        return builder;
    }

}
