package com.github.cta;

import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;

import java.util.List;

/**
 * @author Krzysztof Langner on 16.05.15.
 */
public class Sandbox {

    public static void main(String[] args){
        String fileName = "testdata/NCT01869023.xml";
        XMLTrialFile trailFile = new XMLTrialFile(fileName);
        String[] sentences = trailFile.getEligibilityCriteria().split("\n\n");
        for(String sentence : sentences){
            tokenizeSentence(sentence);
        }

        tokenizeSentence("ecog performance status =<2");
    }

    private static void tokenizeSentence(String line) {
        Tokenizer tokenizer = SimpleTokenizer.INSTANCE;
        System.out.println(line);
        String[] tokens = tokenizer.tokenize(line);
        for(String token : tokens){
            System.out.print("[" + token + "], ");
        }
        System.out.println();
    }

}
