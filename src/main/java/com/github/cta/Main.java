package com.github.cta;

import com.github.cta.extractor.EcogScore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Krzysztof Langner on 16.05.15.
 */
public class Main {

    private static final String TEST_DATA_PATH = "datasets/adenoma";

    public static void main(String[] args){
        String dataPath = (args.length > 0) ? args[0] : TEST_DATA_PATH;
        List<String> files = scanDirectory(dataPath);
        for(String file : files){
            processFile(file);
        }
    }

    private static List<String> scanDirectory(String dataPath) {
        List<String> files = new ArrayList<String>();
        File folder = new File(dataPath);
        if(folder.isDirectory()) {
            //noinspection ConstantConditions
            for (File file : folder.listFiles()) {
                if (file.getName().endsWith(".xml")) {
                    files.add(file.getAbsolutePath());
                }
            }
        }
        else{
            System.out.println("Not directory: " + dataPath);
        }
        return files;
    }

    public static void processFile(String fileName) {
        XMLTrialFile trailFile = new XMLTrialFile(fileName);
        List<Integer> scores = EcogScore.findScore(trailFile.getEligibilityCriteria());
        if(scores.size() > 0) {
            System.out.println("CTA: " + trailFile.getStudyId());
            System.out.print("Labels: ");
            for(int i = 0; i < scores.size(); i++){
                if(i+1 < scores.size()) System.out.print("ECOG " + scores.get(i) + ",");
                else System.out.println("ECOG " + scores.get(i));
            }
            System.out.println();
        }
    }
}
