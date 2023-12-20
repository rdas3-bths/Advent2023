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

        ArrayList<String> allAccepts = new ArrayList<String>();
        for (String key : ruleSet.keySet()) {
            Workflow w = ruleSet.get(key);

            if (w.hasAccept()) {
                if (w.getFinalRule().equals("A")) {
                    allAccepts.add(key + " " + "finalRule,-1");
                }
                for (int i = 0; i < w.getWorkflows().size(); i++) {
                    if (w.getWorkflows().get(i).getResultingWorkflow().equals("A")) {
                        WorkflowRule wfr = w.getWorkflows().get(i);
                        allAccepts.add(key + " " + wfr.getPartName() + wfr.getComparison() + wfr.getCompareValue() + "," + i);
                    }
                }

            }
        }

        long t = 0;
        for (int i = 0; i < allAccepts.size(); i++) {
            ArrayList<String> conditions = new ArrayList<String>();
            String acceptString = allAccepts.get(i);
            int space = acceptString.indexOf(" ");
            int comma = acceptString.indexOf(",");
            String workflowName = acceptString.substring(0, space);
            String workflowRule = acceptString.substring(space+1, comma);
            int index = Integer.parseInt(acceptString.substring(comma+1));

            // starting point for A

            generateConditions(workflowName, workflowRule, index, ruleSet, conditions);

            while (!workflowName.equals("in")) {
                String previousWorkFlow = workflowName;
                workflowName = getAssociatedWorkflow(workflowName, ruleSet);

                Workflow w = ruleSet.get(workflowName);
                String wfr = "";
                int number = -1;
                if (w.getFinalRule().equals(previousWorkFlow)) {
                    wfr = "finalRule";
                }
                else {
                    for (int j = 0; j < w.getWorkflows().size(); j++) {
                        WorkflowRule r = w.getWorkflows().get(j);
                        if (r.getResultingWorkflow().equals(previousWorkFlow)) {
                            wfr = "" + r.getPartName() + r.getComparison() + r.getCompareValue();
                            number = j;
                        }
                    }
                }
                //System.out.println("Previous workflow was: " + previousWorkFlow + " Next workflow is " + workflowName);
                //System.out.println(workflowName + " " + wfr + " " + number);
                generateConditions(workflowName, wfr, number, ruleSet, conditions);

            }



            long minX = 1;
            long maxX = 4000;

            long minM = 1;
            long maxM = 4000;

            long minA = 1;
            long maxA = 4000;

            long minS = 1;
            long maxS = 4000;

            for (String c : conditions) {
                char operator = c.charAt(1);
                char symbol = c.charAt(0);
                int spaceIndex = c.indexOf(" ");
                long value = Integer.parseInt(c.substring(2, spaceIndex));
                String bool = c.substring(spaceIndex+1);

                // if condition is greater than and false:
                // newMin = 1, newMax = number
                // change when newMax is smaller than previous max

                if (operator == '>' && bool.equals("false")) {

                    if (symbol == 'x') if (value < maxX) maxX = value;
                    if (symbol == 'm') if (value < maxM) maxM = value;
                    if (symbol == 'a') if (value < maxA) maxA = value;
                    if (symbol == 's') if (value < maxS) maxS = value;
                }

                // if condition is greater than and true:
                // newMin = number+1, newMax = 4000
                // change when newMin is bigger than previous min

                if (operator == '>' && bool.equals("true")) {
                    long newMin = value+1;

                    if (symbol == 'x') if (newMin > minX) minX = newMin;
                    if (symbol == 'm') if (newMin > minM) minM = newMin;
                    if (symbol == 'a') if (newMin > minA) minA = newMin;
                    if (symbol == 's') if (newMin > minS) minS = newMin;
                }

                // if condition is less than and false:
                // newMin = number, newMax = 4000
                // change when newMin is bigger than previous min

                if (operator == '<' && bool.equals("false")) {
                    long newMin = value;

                    if (symbol == 'x') if (newMin > minX) minX = newMin;
                    if (symbol == 'm') if (newMin > minM) minM = newMin;
                    if (symbol == 'a') if (newMin > minA) minA = newMin;
                    if (symbol == 's') if (newMin > minS) minS = newMin;
                }

                // if condition is less than and true:
                // newMin = 1, newMax = number-1
                // change when newMax is smaller than previous max
                if (operator == '<' && bool.equals("true")) {
                    long newMax = value-1;

                    if (symbol == 'x') if (newMax < maxX) maxX = newMax;
                    if (symbol == 'm') if (newMax < maxM) maxM = newMax;
                    if (symbol == 'a') if (newMax < maxA) maxA = newMax;
                    if (symbol == 's') if (newMax < maxS) maxS = newMax;
                }

            }

            long xDiff = maxX - minX + 1;
            long mDiff = maxM - minM + 1;
            long aDiff = maxA - minA + 1;
            long sDiff = maxS - minS + 1;
            long combinationNumber = xDiff * mDiff * aDiff * sDiff;
            t += combinationNumber;

        }

        System.out.println(t);





//            rfg A:
//            x --> [1, 2440]
//            m --> [1, 2090]
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

    public static String getAssociatedWorkflow(String key, HashMap<String, Workflow> ruleSet) {
        for (String k : ruleSet.keySet()) {
            Workflow w = ruleSet.get(k);
            if (w.getFinalRule().equals(key)) return w.getWorkflowName();

            for (int i = 0; i < w.getWorkflows().size(); i++) {
                WorkflowRule wfr = w.getWorkflows().get(i);
                if (wfr.getResultingWorkflow().equals(key)) return w.getWorkflowName();
            }
        }
        return "";
    }

    public static void generateConditions(String wf, String wfr, int index, HashMap<String, Workflow> ruleSet, ArrayList<String> conditions) {
        Workflow w = ruleSet.get(wf);
        if (wfr.equals("finalRule")) {
            for (int i = 0; i < w.getWorkflows().size(); i++) {
                WorkflowRule singleRule = w.getWorkflows().get(i);
                conditions.add(singleRule + " false");
            }
        }
        else {
            conditions.add(wfr + " true");
            for (int i = index-1; i >= 0; i--) {
                WorkflowRule singleRule = w.getWorkflows().get(i);
               conditions.add(singleRule + " false");
            }
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
