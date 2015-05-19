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


# Redistributing

This code is distributed under BSD3 License. It may be freely redistributed, subject to the provisions of this license.