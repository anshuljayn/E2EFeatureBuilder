package com.acn.uk;

public class Application {

    public static void main(String[] arg) throws Exception {
        GherkinParser gherkinParser = new GherkinParser();

        String mode = arg[0];
        if(mode.equalsIgnoreCase("create"))
            gherkinParser.createE2EFeatures(arg[1],arg[2]);
        else if(mode.equalsIgnoreCase("update"))
            gherkinParser.updateE2EFeature(arg[1],arg[2],arg[3],arg[4]);
        else
            throw new Exception("invalid option provide - valid options [create or update]" +
                    "\n create [source_e2e_feature file or collection folder] [target out folder]" +
                    "\n update [source_e2e_feature file or collection folder] [target out folder] [updated script feature file] [updated scenario id or \"\"]");


        gherkinParser.createE2EFeatures("E2ECollectionFunationality1.feature", "ABC");
    }

}
