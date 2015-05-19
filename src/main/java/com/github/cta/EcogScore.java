package com.github.cta;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Krzysztof Langner on 17.05.15.
 */
public class EcogScore {

    /**
     * Find score based on given text
     * If score can't be read based on given text, then by default it is 4.
     */
    public static List<Integer> findScore(String text){
        String[] lines = text.toLowerCase().split("\n\n");
        for(String line : lines) {
            String[] sentences = line.split("\\.");
            for(String sentence : sentences) {
                if (sentence.contains("ecog ") || sentence.contains("ecog)")) {
                    return scoreFromSentence(sentence);
                }
            }
        }
        return new ArrayList<Integer>();
    }

    /** Extract score from the single sentence */
    private static List<Integer> scoreFromSentence(String line) {
        int score = 0;
        String[] tokens = line.split("\\W+");
        for(String token : tokens){
            if(token.length() == 1 && Character.isDigit(token.charAt(0))){
                int v = Integer.parseInt(token);
                if(v < 5 && v > score){
                    score = v;
                }
            }
        }
        List<Integer> scores = new ArrayList<Integer>();
        for(int i = 0; i <= score; i++){
            scores.add(i);
        }
        return scores;
    }
}
