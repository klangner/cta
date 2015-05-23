package com.github.cta.extractor;

import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Krzysztof Langner on 17.05.15.
 */
public class EcogScoreExtractor {

    private static final Tokenizer TOKENIZER = SimpleTokenizer.INSTANCE;


    /**
     * Find score based on given text
     * If score can't be read based on given text, then by default it is 4.
     */
    public static List<Integer> findScore(String text){
        boolean inclusionCriteria = true;
        String[] lines = text.toLowerCase().split("\n\n");
        for(String line : lines) {
            String[] sentences = line.split("\\.");
            for(String sentence : sentences) {
                String[] tokens = TOKENIZER.tokenize(sentence);
                if(isExclusionHeader(tokens)){
                    inclusionCriteria = false;
                }
                else if (hasToken("ecog", tokens)) {
                    List<Integer> scores = scoreFromSentence(tokens, inclusionCriteria);
                    if(scores.size() > 0)
                        return scores;
                }
            }
        }
        return new ArrayList<Integer>();
    }

    /** Exclusion header should have the following words:
     * "Inclusion" and "Criteria"
     */
    public static boolean isExclusionHeader(String[] tokens){
        return hasToken("exclusion", tokens) && hasToken("criteria", tokens);
    }

    /** Check if given token is on the list */
    private static boolean hasToken(String pattern, String[] tokens){
        for(String token : tokens){
            if(token.equals(pattern)) return true;
        }
        return false;
    }

    /** Extract score from the single sentence */
    private static List<Integer> scoreFromSentence(String[] tokens, boolean inclusive) {
        List<String> normalizedTokens = TokenTranslator.normalizeTokens(tokens);
        List<Integer> scores = scoreFromRange(normalizedTokens);
        if(scores.size() == 0)
            scores = scoreFromRelation(normalizedTokens, inclusive);
        if(scores.size() == 0)
            scores = scoreFromNumbers(normalizedTokens);
        return scores;
    }

    /** Try to extract score by finding range (0-3) in text. */
    private static List<Integer> scoreFromRange(List<String> tokens) {
        List<Integer> scores = new ArrayList<Integer>();
        for(int i = 0; i < tokens.size()-2; i++){
            if(isEcogScore(tokens.get(i)) && tokens.get(i+1).equals("-") && isEcogScore(tokens.get(i+2))){
                int start = Integer.parseInt(tokens.get(i));
                int end = Integer.parseInt(tokens.get(i+2));
                for(int j = start; j <= end; j++){
                    scores.add(j);
                }
                break;
            }
        }
        return scores;
    }

    /** Try to extract score by finding ralation (< 3) in text. */
    private static List<Integer> scoreFromRelation(List<String> tokens, boolean inclusion) {
        boolean ecogKeyword = false;
        for(int i = 0; i < tokens.size()-1; i++){
            if(tokens.get(i).equals("ecog")) ecogKeyword = true;
            String token = tokens.get(i);
            if(isEcogScore(tokens.get(i+1))){
                int ecog = Integer.parseInt(tokens.get(i+1));
                if(token.equals(">")){
                    return listFromScoreRange(0, ecog);
                } else if(token.equals("<")){
                    return listFromScoreRange(0, ecog-1);
                } else if(token.equals(">=")){
                    return listFromScoreRange(0, ecog-1);
                } else if(token.equals("<=")){
                    return listFromScoreRange(0, ecog);
                } else if(token.equals("=")){
                    return listFromScoreRange(ecog, ecog);
                }
                else if(ecogKeyword){
                    // If there was ecog keyword and there is score but no relation then
                    // we should look father
                    return new ArrayList<Integer>();
                }
            }
        }
        return new ArrayList<Integer>();
    }

    /** Try to extract score by finding all numbers in text in ECOG range (0-4) */
    private static List<Integer> scoreFromNumbers(List<String> tokens) {
        int score = -1;
        for(String token : tokens){
            if(isEcogScore(token)){
                int v = Integer.parseInt(token);
                if(v < 5 && v > score){
                    score = v;
                }
            }
        }
        return listFromScoreRange(0, score);
    }

    /** Convert range to list elements */
    private static List<Integer> listFromScoreRange(int from, int to){
        List<Integer> scores = new ArrayList<Integer>();
        for(int i = from; i <= to; i++){
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
