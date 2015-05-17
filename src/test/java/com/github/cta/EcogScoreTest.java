package com.github.cta;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * @author Krzysztof Langner on 17.05.15.
 */
public class EcogScoreTest {

    private final static String TEST_DATA_FOLDER = "testdata/";


    @Test
    public void ecogScore() throws IOException {
        List<String> lines = Files.readLines(new File(TEST_DATA_FOLDER + "ecog.txt"), Charsets.UTF_8);
        for(String line : lines){
            if(!line.startsWith("#")){
                String[] tokens = line.split(":");
                if(tokens.length == 2) {
                    String fileName = tokens[0];
                    int score = Integer.parseInt(tokens[1].split("=")[1]);
                    testScore(fileName, score);
                }
            }
        }
    }

    private void testScore(String fileName, int expectedScore) {
        XMLTrialFile trailFile = new XMLTrialFile(TEST_DATA_FOLDER+fileName);
        int ecog = EcogScore.findScore(trailFile.getEligibilityCriteria());
        assertEquals(fileName, expectedScore, ecog);
    }
}
