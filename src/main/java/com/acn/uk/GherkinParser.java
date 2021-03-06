package com.acn.uk;

import io.cucumber.gherkin.Gherkin;
import io.cucumber.messages.IdGenerator;
import io.cucumber.messages.Messages.Envelope;
import io.cucumber.messages.Messages.GherkinDocument;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class GherkinParser {

    private static final String FEATURE = System.getProperty("user.dir") + "\\src\\test\\resources\\features\\";
    private IdGenerator idGenerator = new IdGenerator.Incrementing();
    private final Map<String, String> scenarioMap = new HashMap<>();

    /**
     * @param inputFolder  E2E collection features
     * @param outputFolder output folder root for generated feature files
     * @throws IOException for file io
     */
    public void createE2EFeatures(String inputFolder, String outputFolder) throws IOException {

        List<String> paths = Files.walk(Paths.get(FEATURE + inputFolder)).map(Path::toString).filter(f -> f.endsWith(".feature")).collect(Collectors.toList());

        //list of feature file
        List<Envelope> envelopes = Gherkin.fromPaths(paths, false, true, false, idGenerator).collect(Collectors.toList());
        Map<String, String> scenarioMap = new HashMap<>();
        for (Envelope e : envelopes) {
            GherkinDocument gherkinDocument = e.getGherkinDocument();

            //feature file
            GherkinDocument.Feature feature = gherkinDocument.getFeature();

            //get the output folder
            String output = outputFile(outputFolder, gherkinDocument.getUri());

            // scenario list in a feature file
            for (GherkinDocument.Feature.FeatureChild f : feature.getChildrenList()) {
                GherkinDocument.Feature.Scenario scenario = f.getScenario();
                try {
                    parseScenario(scenario, output);
                } catch (FeatureGenException featureGenException) {
                    featureGenException.printStackTrace();
                    System.err.println("check : " + gherkinDocument.getUri());
                }
            }
        }
    }

    /**
     * @param inputFolder        E2E collection features
     * @param outputFolder       output folder root for generated feature files
     * @param updatedFeature     source feature file updated
     * @param updatedScenarioTag souce scenario updated
     * @throws IOException for file io
     */
    public void updateE2EFeature(String inputFolder, String outputFolder, String updatedFeature, String updatedScenarioTag) throws IOException {
        List<String> paths = Files.walk(Paths.get(FEATURE + inputFolder)).map(Path::toString).filter(f -> f.endsWith(".feature")).collect(Collectors.toList());

        //list of feature file
        List<Envelope> envelopes = Gherkin.fromPaths(paths, false, true, false, idGenerator).collect(Collectors.toList());

        for (Envelope e : envelopes) {

            GherkinDocument gherkinDocument = e.getGherkinDocument();

            //feature file
            GherkinDocument.Feature feature = gherkinDocument.getFeature();
            String output = outputFile(outputFolder, gherkinDocument.getUri());

            // scenario list in a feature file
            for (GherkinDocument.Feature.FeatureChild f : feature.getChildrenList()) {
                GherkinDocument.Feature.Scenario scenario = f.getScenario();
                AtomicBoolean updateScenario = new AtomicBoolean(false);

                scenario.getStepsList().forEach(step -> {
                    String stepDef = step.getText();
                    if (stepDef.contains("call")) {
                        if (updatedScenarioTag.length() > 0 && stepDef.contains(updatedFeature) && stepDef.contains(updatedScenarioTag)) {
                            updateScenario.set(true);
                        }
                        if (updatedScenarioTag.length() == 0 && stepDef.contains(updatedFeature)) {
                            updateScenario.set(true);
                        }
                    }
                });

                if (updateScenario.get()) {
                    try {
                        parseScenario(scenario, output);
                    } catch (FeatureGenException featureGenException) {
                        featureGenException.printStackTrace();
                    }
                }
            }
        }
    }

    private String outputFile(String outputFolder, String uri) {
        return FEATURE + outputFolder + uri.substring(uri.lastIndexOf("\\")).replace(".feature", "");
    }

    private void parseScenario(GherkinDocument.Feature.Scenario scenario, String output) throws IOException, FeatureGenException {

        StringBuilder sb = new StringBuilder();
        List<String> tagList = scenario.getTagsList().stream()
                .map(GherkinDocument.Feature.Tag::getName)
                .collect(Collectors.toList());

        sb.append("# auto generated on ").append(new Date()).append(" by ").append(System.getProperty("user.name")).append("\n\n");
        sb.append(tagList.size() > 0 ? String.join(" ", tagList) + "\n" : "");
        sb.append("Feature: ").append(scenario.getName()).append("\n\n");
        AtomicBoolean solo = new AtomicBoolean(false);

        for(GherkinDocument.Feature.Step step:scenario.getStepsList()){
            String stepDef = step.getText().replaceAll("\\s+", " ");

            if (stepDef.startsWith("call")) { //i.e. calling another feature file
                String feature_sce = stepDef.replace("call", "").trim();
                if (!scenarioMap.containsKey(feature_sce)) {
                    String detailedScenario = null;
                    try {
                        detailedScenario = getTargetScenario(feature_sce);
                    } catch (Exception e) {
                        System.err.println(output + ":" + scenario.getName() + " : Feature not created");
                        e.printStackTrace();
                        return;
                    }
                    scenarioMap.put(feature_sce, detailedScenario);
                }
                sb.append("\n");
                sb.append(scenarioMap.get(feature_sce)).append("\n");
                solo.set(false);
            } else { //i.e. actual step def in the e2e combination scneario
                if (!solo.get()) sb.append("Scenario: ").append(stepDef).append("\n");
                sb.append(step.getKeyword()).append(step.getText()).append("\n");
                //check datatable
                GherkinDocument.Feature.Step.DataTable dataTable = step.getDataTable();
                int rowCount = dataTable.getRowsCount();
                if (rowCount > 0) {
                    dataTable.getRowsList().stream().forEach(row -> {
                        List<String> cells = row.getCellsList().stream()
                                .map(GherkinDocument.Feature.TableRow.TableCell::getValue)
                                .collect(Collectors.toList());
                        cells.forEach(cell -> sb.append("|").append(cell));
                        sb.append("|\n");
                    });
                }
                solo.set(true);
            }
        }

            Pattern pattern = Pattern.compile("\\[(.*?)\\]");
            Matcher matcher = pattern.matcher(scenario.getName());
            String ffileName;
            if (matcher.find()) {
                ffileName = matcher.group(1).replace(":","_");
                if(!idCheck(ffileName)) {
                    throw new FeatureGenException(
                            "invalid id pattern [" + scenario.getName() + "]" +
                            "\nallowed characters A-Z, a-z, 0-9, _, -; starts and end with alphanumeric character, consecutive - or _ not allowed");
                }
            }
            else {
                throw new FeatureGenException("no ID pattern found for unique feature/Scenario: [" + scenario.getName() + "]");
            }

            File file = new File(output);
            if(!file.exists()) file.mkdirs();

            File featureFile = new File(output + "\\" + ffileName + ".feature");
            if(featureFile.exists()){
                System.out.println("file exist..... " + featureFile);
                if(featureFile.delete()) System.out.println("deleted......");
            }
            System.out.println("generating ---------->" + output + "\\" + ffileName + ".feature");
            featureFile.createNewFile();

            BufferedWriter bw = new BufferedWriter(new FileWriter(featureFile, true));
            PrintWriter out = new PrintWriter(bw);
            out.println(sb.toString());
            out.flush();
            out.close();
    }

    private String getTargetScenario(String feature_sce) throws Exception {
        String featureFile = feature_sce.split(" ")[0].trim();
        String scenarioId = feature_sce.split(" ")[1].trim();
        featureFile = featureFile.endsWith(".feature")?featureFile:featureFile+".feature";

        List<String> paths = Files.walk(Paths.get(FEATURE + featureFile)).map(Path::toString).filter(f -> f.endsWith(".feature")).collect(Collectors.toList());

        //list of feature file
        List<Envelope> envelopes = Gherkin.fromPaths(paths, false, true, false, idGenerator).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        for (Envelope e : envelopes) {
            GherkinDocument gherkinDocument = e.getGherkinDocument();

            if(!e.hasGherkinDocument()) throw new FeatureGenException("'" + featureFile + "' Feature File invalid");

            //Feature file
            GherkinDocument.Feature feature = gherkinDocument.getFeature();
            // scenario list in a feature file
            for (GherkinDocument.Feature.FeatureChild f : feature.getChildrenList()) {
                //scenario
                GherkinDocument.Feature.Scenario scenario = f.getScenario();
                List<String> tagList = scenario.getTagsList().stream()
                        .map(GherkinDocument.Feature.Tag::getName)
                        .collect(Collectors.toList());

                if (tagList.contains(scenarioId)) {
                    sb.append(tagList.size() > 0 ? String.join(" ", tagList) + "\n" : "");
                    sb.append(scenario.getKeyword()).append(": ").append(scenario.getName()).append("\n");

                    scenario.getStepsList().forEach(step -> {
                        sb.append(step.getKeyword()).append(step.getText()).append("\n");
                        GherkinDocument.Feature.Step.DataTable dataTable = step.getDataTable();
                        int rowCount = dataTable.getRowsCount();
                        if (rowCount > 0) {
                            dataTable.getRowsList().stream().forEach(row -> {
                                List<String> cells = row.getCellsList().stream()
                                        .map(GherkinDocument.Feature.TableRow.TableCell::getValue)
                                        .collect(Collectors.toList());
                                cells.forEach(cell -> sb.append("|").append(cell));
                                sb.append("|\n");
                            });
                        }
                    });

                    if (scenario.getKeyword().equalsIgnoreCase("Scenario Outline")) {
                        sb.append(getExampleTables(scenario)).append("\n");
                    }
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }

    private String getExampleTables(GherkinDocument.Feature.Scenario scenario) {
        StringBuilder sb = new StringBuilder();
        scenario.getExamplesList().forEach(example -> {

            List<String> tagList = example.getTagsList().stream()
                    .map(GherkinDocument.Feature.Tag::getName)
                    .collect(Collectors.toList());

            sb.append(tagList.size() > 0 ? String.join(" ", tagList) + "\n" : "");
            sb.append(example.getKeyword()).append(":").append(example.getDescription()).append("\n");

            example.getTableHeader().getCellsList().stream()
                    .map(GherkinDocument.Feature.TableRow.TableCell::getValue)
                    .collect(Collectors.toList()).forEach(cell -> sb.append("|").append(cell));
            sb.append("|\n");

            example.getTableBodyList().stream().forEach(row -> {
                row.getCellsList().stream()
                        .map(GherkinDocument.Feature.TableRow.TableCell::getValue)
                        .collect(Collectors.toList())
                        .forEach(cell -> sb.append("|").append(cell));
                sb.append("|\n");
            });
            sb.append("\n");
        });

        return sb.toString();
    }

    public boolean idCheck(String str){
        if(str.length() == 0) return false;
        if(str.replaceAll("[\\w-]","").length() > 0) return false;
        if(str.startsWith("-") ||str.startsWith("_")) return false;
        if(str.endsWith("-") || str.endsWith("_")) return false;
        if(str.contains("-_") || str.contains("_-") || str.contains("--") || str.contains("__")) return false;

        return true;
    }
}