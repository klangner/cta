package com.github.cta;

/**
 * @author Krzysztof Langner on 17.05.15.
 */
public class EcogScore {

    /**
     * Find score based on given text
     * If score can't be read based on given text, then by default it is 4.
     */
    public static int findScore(String text){
        if(text.toLowerCase().contains("ecog")) {
            return 0;
        }
        return 4;
    }
}
