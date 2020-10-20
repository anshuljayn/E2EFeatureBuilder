# E2EFeatureBuilder
Generate e2e feature file using combination of other feature file

### Usage:


java -jar E2EFeatureBuilder-0.0.1-withDependency.jar [create/update] [...]
 

- create [source_e2e_feature file or collection folder] [target out folder]
- update [source_e2e_feature file or collection folder] [target out folder] [updated script feature file] [updated scenario id or ""]

### Input collection Feature file:
```
Feature: End-2-End Test Case Functionality 1

  @e2e1-1 @somemore
    @someextra
  Scenario: [E2E1-0] Some description 0
    Given a step definitin
    Given call featurefile1.feature @id:1
    Given call featurefile2.feature @id:2
    Given call path/featurefile3.feature @sometag
    Given another step
    Given call DoNothing.feature @id:6


  Scenario: [E2E1-1] Some description 1
    Given a step definitin
    Given call featurefile1.feature @id:6
    Given call featurefile2.feature @id:1
    Given call path/featurefile3.feature @sometag2
    Given another step 1
    Given another step 2
    Given call DoNothing.feature @id:2
```
where - 

    Given call <path to feature file> <scenario tag from the featue file>

    
   gets the respective scenario from the given feature file. Any other step definition is used as-is.

##### Pre-Conditions:
- Use only Scenario in the E2E journey feature file
- Use valid scenario id with-in square brackets in the scenario description
- Valid id - 
  - allowed characters A-Z, a-z, 0-9, _, -
  - can't start with or end with _ or -
  - can't have consecutive _ or -
  
  
### Error Handling

##### *Invalid option provided* 

```
Exception in thread "main" java.lang.Exception: invalid option provide - valid options [create or update]
 create [source_e2e_feature file or collection folder] [target out folder]
 update [source_e2e_feature file or collection folder] [target out folder] [updated script feature file] [updated scenario id or ""]
        at com.acn.uk.Application.main(Application.java:15)
```

##### *No parameter provided*
```
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: 0
        at com.acn.uk.Application.main(Application.java:8)
```

##### *No id provided in Scenario description* 

```
com.acn.uk.FeatureGenException: no ID pattern found for unique feature/Scenario: [Some description 4]
	at com.acn.uk.GherkinParser.parseScenario(GherkinParser.java:178)
	at com.acn.uk.GherkinParser.createE2EFeatures(GherkinParser.java:54)
	at com.acn.uk.Application.main(Application.java:20)
check : C:\Users\user\my-proejct\src\test\resources\features\E2ECollection\E2EJourney1\Module1\E2ECollectionFunationality1.feature
```

##### *Invalid id provided in Scenario description* 
```
com.acn.uk.FeatureGenException: invalid id pattern [[E2E1- 3] Some description 4]
allowed characters A-Z, a-z, 0-9, _, -; starts and end with alphanumeric character, consecutive - or _ not allowed
	at com.acn.uk.GherkinParser.parseScenario(GherkinParser.java:173)
	at com.acn.uk.GherkinParser.createE2EFeatures(GherkinParser.java:54)
	at com.acn.uk.Application.main(Application.java:20)
check : C:\Users\user\my-proejct\src\test\resources\features\E2ECollection\E2EJourney1\Module1\E2ECollectionFunationality1.feature
```

##### *Source feature file not valid* 
```
C:\Users\user\my-proejct\src\test\resources\features\E2E\E2ECollectionFunationality1:[E2E1-0] Some description 0 : Feature not created
com.acn.uk.FeatureGenException: 'featurefile2.feature' Feature File invalid
	at com.acn.uk.GherkinParser.getTargetScenario(GherkinParser.java:211)
	at com.acn.uk.GherkinParser.parseScenario(GherkinParser.java:135)
	at com.acn.uk.GherkinParser.createE2EFeatures(GherkinParser.java:53)
	at com.acn.uk.Application.main(Application.java:20)
```
