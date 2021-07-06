package com.nordea.demo.xml;

import javax.xml.bind.annotation.XmlElement;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

public class Sentence {

    //List<String> word;

    Collection<String> word;


    @XmlElement
    public Collection<String> getWord() {
        return word;
    }

    public void setWord(Collection<String> word) {
        this.word = word;
    }

    public void setWord(String _word) {
        if(word == null){
            word = new TreeSet<String>(Collator.getInstance());
        }
        word.add(_word);
    }

    public StringBuilder xmlBuilder() {
        StringBuilder builder = new StringBuilder();
        word.stream().forEach(w -> {
            builder.append("<word>").append(w).append("</word>");
        });
        return builder;
    }

    public StringBuilder cvsBuilder(int index) {
        StringBuilder builder = new StringBuilder();
        builder.append("Sentence ").append(index);
        word.stream().forEach(w -> {
            builder.append(", ").append(w);
        });
        return builder;
    }

}
