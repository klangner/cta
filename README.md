# Clinical Trials Study parser

Parser for clinical trials studies (www.clinicaltrials.gov)

Functionality:

* Extracts ECOG Performance Score from Eligibility Criteria
 
## Compiling and running

This program is build with [Gradle](https://gradle.org/).

To build jar with all dependencies run:
```sh
gradlew assemble
```

To run program:
```sh
java -jar build/libs/cta-1.0.jar <path_to_studies>
```

## Hadoop Map Reduce

Before running MapReduce Job it is necessary to convert XML files into format more friendly to Hadoop:

```sh
java -classpath build/libs/cta-1.0.jar com.github.cta.ConvertStudies <input_folder> <output_file>
```

* input_folder - Folder with study files in XML format.
* output_file - file name where converted studies will be saved.

Run Hadoop Job:

```sh
 hadoop jar build/libs/cta-1.0.jar com.github.cta.hadoop.EcogScore hdfs/input hdfs/output
```

Browse calculated scores:

```sh
cat hdfs/output/part-r-00000
```


# Redistributing

This code is distributed under BSD3 License. It may be freely redistributed, subject to the provisions of this license.
