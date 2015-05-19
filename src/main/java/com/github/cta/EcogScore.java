package com.github.cta;

import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Krzysztof Langner on 17.05.15.
 */
public class EcogScore {

    private static final Tokenizer TOKENIZER = SimpleTokenizer.INSTANCE;


    /**
     * Find score based on given text
     * If score can't be read based on given text, then by default it is 4.
     */
    public static List<Integer> findScore(String text){
        String[] lines = text.toLowerCase().split("\n\n");
        for(String line : lines) {
            String[] sentences = line.split("\\.");
            for(String sentence : sentences) {
                String[] tokens = TOKENIZER.tokenize(sentence);
                if (hasToken("ecog", tokens)) {
                    List<Integer> scores = scoreFromSentence(tokens);
                    if(scores.size() > 0)
                        return scores;
                }
            }
        }
        return new ArrayList<Integer>();
    }

    /** Check if given token is on the list */
    private static boolean hasToken(String pattern, String[] tokens){
        for(String token : tokens){
            if(token.equals(pattern)) return true;
        }
        return false;
    }

    /** Extract score from the single sentence */
    private static List<Integer> scoreFromSentence(String[] tokens) {
        List<Integer> scores = scoreFromRange(tokens);
        if(scores.size() == 0)
            scores = scoreFromNumbers(tokens);
        return scores;
    }

    /** Try to extract score by finding range (0-3) in text. */
    private static List<Integer> scoreFromRange(String[] tokens) {
        List<Integer> scores = new ArrayList<Integer>();
        for(int i = 0; i < tokens.length-2; i++){
            if(isEcogScore(tokens[i]) && tokens[i+1].equals("-") && isEcogScore(tokens[i+2])){
                int start = Integer.parseInt(tokens[i]);
                int end = Integer.parseInt(tokens[i+2]);
                for(int j = start; j <= end; j++){
                    scores.add(j);
                }
                break;
            }
        }
        return scores;
    }

    /** Try to extract score by finding all numbers in text in ECOG range (0-4) */
    private static List<Integer> scoreFromNumbers(String[] tokens) {
        int score = -1;
        for(String token : tokens){
            if(isEcogScore(token)){
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

    /** Check if given token represents ECOG score. Must be a number from 0 to 5 */
    private static boolean isEcogScore(String token){
        if(token.length() == 1) {
            char c = token.charAt(0);
            return (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5');
        }
        return false;
    }
}
