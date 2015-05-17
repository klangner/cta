package com.github.cta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.google.common.io.Files;

/**
 * @author Krzysztof Langner on 16.05.15.
 */
public class Main {

    private static final String TEST_DATA_PATH = "data";

    public static void main(String[] args){
        List<String> files = scanDirectory(TEST_DATA_PATH);
        for(String file : files){
            processFile(file);
        }
    }

    private static List<String> scanDirectory(String data) {
        List<String> files = new ArrayList<String>();
        File folder = new File(data);
        for(File file : folder.listFiles()){
            if(file.getName().endsWith(".xml")){
                files.add(file.getAbsolutePath());
            }
        }
        return files;
    }

    private static void processFile(String fileName) {
        XMLTrialFile trailFile = new XMLTrialFile(fileName);
        int ecog = EcogScore.findScore(trailFile.getEligibilityCriteria());
        if(ecog == 0) {
            System.out.println(fileName);
        }
    }
}
