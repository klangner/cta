package com.github.cta.extractor;

import opennlp.tools.tokenize.SimpleTokenizer;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertTrue;


public class TokenTranslatorTest {

    @Test
    public void equalLess() throws IOException {
        checkToken("=", "a = b");
        checkToken("=", "a == b");
        checkToken("<", "a < b");
        checkToken(">", "a > b");
        checkToken("<=", "a =< b");
        checkToken("<=", "a <= b");
        checkToken(">=", "a => b");
        checkToken(">=", "a >= b");
//        * ["less", "than", "or", "equal", "to"] -> "<="
//        * ["less", "than"] -> "<"
//        * ["equal", "to", "or", "less", "than"] -> "<="
//        * ["greater", "than", "or", "equal", "to"] -> ">="
//        * ["greater", "than"] -> ">"
//        * ["equal", "to", "or", "greater", "than"] -> ">="
    }

    private void checkToken(String token, String text) {
        List<String> tokens = TokenTranslator.normalizeTokens(SimpleTokenizer.INSTANCE.tokenize(text));
        boolean found = false;
        for(String t : tokens){
            if(token.equals(t)){
                found = true;
                break;
            }
        }

        assertTrue(text, found);
    }

}
