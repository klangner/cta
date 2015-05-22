package com.github.cta;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Convert studies XML files into Hadoop friendly files (each file as single record with study id as key).
 * New lines are converted to '|'
 */
public class ConvertStudies {

    public static void main(String[] args) throws IOException {
        if(args.length != 2){
            printUsage();
            System.exit(-1);
        }
        String inputDataPath =  args[0];
        Writer writer = new FileWriter(args[1]);
        List<String> files = scanDirectory(inputDataPath);
        for(String fileName : files){
            XMLTrialFile trailFile = new XMLTrialFile(fileName);
            String field = trailFile.getEligibilityCriteria().replaceAll("\n", "|");
            writer.append(trailFile.getStudyId()).append(":").append(field).append("\n");
        }
        writer.close();
        System.out.println("Finished processing " + files.size() + " files.");
    }

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("ConvertStudies <input_path> <output_file>");
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
}
