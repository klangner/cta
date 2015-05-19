package com.github.cta;

import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;

/**
 * @author Krzysztof Langner on 16.05.15.
 */
public class Sandbox {

    public static void main(String[] args){
        tokenizeSentence("(ecog) score (ps) >= 2");
        tokenizeSentence("(ecog) score (ps) 0-2");
        tokenizeSentence("(ecog) score (ps) 0, 1 or 2");
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
