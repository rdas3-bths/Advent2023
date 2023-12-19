public class WorkflowRule {
    private char partName;
    private char comparison;
    private int compareValue;
    private String resultingWorkflow;

    public WorkflowRule(char partName, char comparison, int compareValue, String resultingWorkflow) {
        this.partName = partName;
        this.comparison = comparison;
        this.compareValue = compareValue;
        this.resultingWorkflow = resultingWorkflow;
    }

    public WorkflowRule(String workflow) {
        // a<2006:qkq
        this.partName = workflow.charAt(0);
        this.comparison = workflow.charAt(1);
        String temp = workflow.substring(2, workflow.indexOf(":"));
        this.compareValue = Integer.parseInt(temp);
        this.resultingWorkflow = workflow.substring(workflow.indexOf(":")+1);
    }

    public char getPartName() {
        return partName;
    }

    public void setPartName(char partName) {
        this.partName = partName;
    }

    public char getComparison() {
        return comparison;
    }

    public void setComparison(char comparison) {
        this.comparison = comparison;
    }

    public int getCompareValue() {
        return compareValue;
    }

    public void setCompareValue(int compareValue) {
        this.compareValue = compareValue;
    }

    public String getResultingWorkflow() {
        return resultingWorkflow;
    }

    public void setResultingWorkflow(String resultingWorkflow) {
        this.resultingWorkflow = resultingWorkflow;
    }

    public String toString() {
        return partName + "" + comparison + compareValue + ":" + resultingWorkflow;
    }

    public boolean getWorkflowResult(Part p) {
        int x = p.getX();
        int m = p.getM();
        int a = p.getA();
        int s = p.getS();

        if (partName == 'x') {
            if (comparison == '>') {
                return x > compareValue;
            }
            else {
                return x < compareValue;
            }
        }

        else if (partName == 'm') {
            if (comparison == '>') {
                return m > compareValue;
            }
            else {
                return m < compareValue;
            }
        }

        else if (partName == 'a') {
            if (comparison == '>') {
                return a > compareValue;
            }
            else {
                return a < compareValue;
            }
        }

        else if (partName == 's') {
            if (comparison == '>') {
                return s > compareValue;
            }
            else {
                return s < compareValue;
            }
        }

        return false;

    }
}
