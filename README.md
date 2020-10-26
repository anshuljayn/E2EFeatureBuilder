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
    Given T0 call featurefile1.feature @id:1 @rtvm @jira
    Given T1 call featurefile2.feature @id:2 @rtvm @jira
    Given T2 call path/featurefile3.feature @sometag @rtvm @jira
    Given another step
    Given T3 call DoNothing.feature @id:6 @rtvm @jira


  Scenario: [ID:E2E1-1] Some description 1
    Given a step definitin
    Given T0 call featurefile1.feature @id:6 @rtvm @jira
    Given T1 call featurefile2.feature @id:1 @rtvm @jira
    Given T2 call path/featurefile3.feature @sometag2 @rtvm @jira
    Given another step 1
    Given another step 2
    Given T2+10 call DoNothing.feature @id:2 @rtvm @jira
```
where - 

    Given [transaction Day identifiers] call [feature file path] [unique script id in the feature file] [functional tag id for RTVM] [JIRA ID For the test case]
    
   gets the respective scenario from the given feature file. Any other step definition is used as-is and added as pre-req to next feature:scenario call.

##### Pre-Conditions:
- Use only Scenario in the E2E journey feature file
- Use valid scenario id with-in square brackets in the scenario description
- Valid id - 
  - allowed characters A-Z, a-z, 0-9, _, -, : (':' will be replaced with '_' while createing the output feature file)
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
check : C:\Users\user\my-proejct\src\test\resources\features\E2ECollection\E2EJourney1\Module1\E2ECollectionFunctionality1.feature
```

##### *Invalid id provided in Scenario description* 
```
com.acn.uk.FeatureGenException: invalid id pattern [[E2E1- 3] Some description 4]
allowed characters A-Z, a-z, 0-9, _, -; starts and end with alphanumeric character, consecutive - or _ not allowed
	at com.acn.uk.GherkinParser.parseScenario(GherkinParser.java:173)
	at com.acn.uk.GherkinParser.createE2EFeatures(GherkinParser.java:54)
	at com.acn.uk.Application.main(Application.java:20)
check : C:\Users\user\my-proejct\src\test\resources\features\E2ECollection\E2EJourney1\Module1\E2ECollectionFunctionality1.feature
```

##### *Invalid step to call feature:scenario; missing params* 
```
com.acn.uk.FeatureGenException: invalid step to call feature:scenario; missing params:
correct patter as below:
Given [transaction Day identifiers] call [feature file path] [unique script id in the feature file] [functional tag id for RTVM] [JIRA ID For the test case]
check: [E2E1-2] Some description 2
line: 26
column: 5
Given T1 call CheckJourney @id:4 @rtvm1
	at com.acn.uk.GherkinParser.parseScenario(GherkinParser.java:147)
	at com.acn.uk.GherkinParser.createE2EFeatures(GherkinParser.java:49)
	at com.acn.uk.Application.main(Application.java:19)
check : C:\Users\user\my-proejct\src\test\resources\features\E2ECollection\E2EJourney1\Module1\E2ECollectionFunctionality1.feature
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
