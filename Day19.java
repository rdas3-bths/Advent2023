import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

public class Day19 {
    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("data/Day19_Input_Parts");
        ArrayList<Part> allParts = new ArrayList<Part>();
        for (String line : fileData) {
            line = line.substring(1, line.length()-1);
            String[] parts = line.split(",");
            int x = -1;
            int m = -1;
            int a = -1;
            int s = -1;
            for (String p : parts) {
                String[] eachPart = p.split("=");
                if (eachPart[0].equals("x"))
                    x = Integer.parseInt(eachPart[1]);
                if (eachPart[0].equals("m"))
                    m = Integer.parseInt(eachPart[1]);
                if (eachPart[0].equals("a"))
                    a = Integer.parseInt(eachPart[1]);
                if (eachPart[0].equals("s")) {
                    s = Integer.parseInt(eachPart[1]);
                    Part singlePart = new Part(x, m, a, s);
                    allParts.add(singlePart);
                }
            }
        }

        ArrayList<String> workflowFileData = getFileData("data/Day19_Input_Workflows");
        HashMap<String, Workflow> ruleSet = new HashMap<String, Workflow>();

        for (String line : workflowFileData) {
            String workflowName = line.substring(0, line.indexOf("{"));
            String workflows = line.substring(line.indexOf("{") + 1, line.length()-1);
            String[] workflowList = workflows.split(",");
            String finalRule = workflowList[workflowList.length-1];
            ArrayList<WorkflowRule> rules = new ArrayList<WorkflowRule>();

            for (int i = 0; i < workflowList.length-1; i++) {
                String singleWorkflow = workflowList[i];
                WorkflowRule w = new WorkflowRule(singleWorkflow);
                rules.add(w);
            }
            Workflow work = new Workflow(workflowName, rules, finalRule);
            ruleSet.put(workflowName, work);
        }

        // start checking parts
        for (int i = 0; i < allParts.size(); i++) {
            Part p = allParts.get(i);
            String result = "in";
            while (!result.equals("A") && !result.equals("R")) {
                Workflow w = ruleSet.get(result);
                result = w.checkAllRules(p);
            }
            if (result.equals("A"))
                p.accept();
        }

        int total = 0;
        for (Part p : allParts) {
            if (p.isAccepted())
                total += (p.getX() + p.getM() + p.getA() + p.getS());
        }

        for (String key : ruleSet.keySet()) {
            Workflow w = ruleSet.get(key);
            ArrayList<String> allAccepts = new ArrayList<String>();
            if (w.hasAccept()) {
                if (w.getFinalRule().equals("A")) {
                    allAccepts.add("finalRule");
                }
                for (int i = 0; i < w.getWorkflows().size(); i++) {
                    if (w.getWorkflows().get(i).getResultingWorkflow().equals("A")) {
                        WorkflowRule wfr = w.getWorkflows().get(i);
                        allAccepts.add("" + wfr.getPartName() + wfr.getComparison() + wfr.getCompareValue());
                    }
                }

            }

            for (int i = 0; i < allAccepts.size(); i++) {
                System.out.println(key + " " + allAccepts.get(i));

            }

//            rfg A:
//            x --> [1, 2439]
//            m --> [1, 2089]
//            a --> [1, 752]
//            s --> [1, 1350]
//            2440 * 2090 * 752 * 1350
//            combinations: 5,177,113,920,000
//
//            px A:
//            x --> [1, 4000]
//            m --> [2091, 4000]
//            a --> [1, 752]
//            s --> [1, 1350]
//            4000 * 1910 * 752 *  1350
//            combinations: 7,756,128,000,000
//
//            total = 12,933,241,920,000
//
//
//            in{s<1351:px,R}
//            rfg{x>2440:R,A}
//            px{a>752:R,m>2090:A,rfg}
        }


    }

    public static ArrayList<String> getFileData(String fileName) {
        ArrayList<String> fileData = new ArrayList<String>();
        try {
            File f = new File(fileName);
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (!line.equals(""))
                    fileData.add(line);
            }
            return fileData;
        }
        catch (FileNotFoundException e) {
            return fileData;
        }
    }
}
