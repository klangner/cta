package com.github.cta.hadoop;

import com.github.cta.extractor.EcogScoreExtractor;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.List;


/**
 * MapReduce application for extracting ECOG score using Hadoop framework
 * @author Krzysztof Langner on 2015-05-21.
 */
public class EcogScore {

    public static class ScoreMapper extends Mapper<LongWritable, Text, Text, Text> {

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            int index = line.indexOf(':');
            String studyId = value.toString().substring(0, index);
            String criteria = line.substring(index+1).replaceAll("\\|", "\n");
            List<Integer> scores = EcogScoreExtractor.findScore(criteria);
            String scoreText = "";
            for(int i = 0; i < scores.size(); i++){
                scoreText += "ECOG " + scores.get(i);
                if(i+1 < scores.size())  scoreText += ",";
            }
            context.write(new Text(studyId), new Text(scoreText));
        }
    }

    public static class ScoreReducer extends Reducer<Text, Text, Text, Text> {

        public void reduce(Text key, Iterable<Text> scores, Context context) throws IOException, InterruptedException {
            context.write(key, scores.iterator().next());
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "ECOG Score");
        job.setJarByClass(EcogScore.class);
        job.setMapperClass(ScoreMapper.class);
        job.setReducerClass(ScoreReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
