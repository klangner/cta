package com.github.cta.extractor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Krzysztof Langner on 17.05.15.
 */
public class TokenTranslator {


    /**
     * Normalize tokens by translating:
     *  ["=","<"] -> "<="
     *  ["<","="] -> "<="
     *  ["=",">"] -> ">="
     *  [">","="] -> ">="
     *  ["="] -> "="
     *  ["=","="] -> "="
     *  ["less", "than", "or", "equal", "to"] -> "<="
     *  ["less", "than"] -> "<"
     *  ["equal", "to", "or", "less", "than"] -> "<="
     *  ["greater", "than", "or", "equal", "to"] -> ">="
     *  ["greater", "than"] -> ">"
     *  ["equal", "to", "or", "greater", "than"] -> ">="
     */
    public static List<String> normalizeTokens(String[] tokens) {
        List<String> normalized = new ArrayList<String>();
        for(int i = 0; i < tokens.length; i++){
            if(isRelation(tokens[i]) && i+1 < tokens.length && isRelation(tokens[i+1])){
                String t = normalizeRelations(tokens[i], tokens[i+1]);
                normalized.add(t);
                i++;
            }
            else if(tokens[i].equals("==")){
                normalized.add("=");
            }
            else {
                normalized.add(tokens[i]);
            }
        }
        return normalized;
    }

    private static boolean isRelation(String token) {
        return token.equals(">") || token.equals("<") || token.equals("=");
    }

    private static String normalizeRelations(String t1, String t2) {
        if(t1.equals("=")){
            if(t2.equals("=")) return "=";
            else return t2+t1;
        }
        return t1+t2;
    }
}
