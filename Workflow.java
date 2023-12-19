import java.util.ArrayList;

public class Workflow {
    private String workflowName;
    private ArrayList<WorkflowRule> workflows;
    private String finalRule;

    public Workflow(String workflowName, ArrayList<WorkflowRule> workflows, String finalRule) {
        this.workflowName = workflowName;
        this.workflows = workflows;
        this.finalRule = finalRule;
    }

    public String toString() {
        return workflowName + workflows + "," + finalRule;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public ArrayList<WorkflowRule> getWorkflows() {
        return workflows;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public String getFinalRule() {
        return finalRule;
    }

    public void setFinalRule(String finalRule) {
        this.finalRule = finalRule;
    }

    public String checkAllRules(Part p) {
        for (WorkflowRule w : workflows) {
            boolean check = w.getWorkflowResult(p);
            if (check == true) {
                return w.getResultingWorkflow();
            }
        }
        return finalRule;
    }

    public boolean hasAccept() {
        if (finalRule.equals("A")) return true;

        for (WorkflowRule w : workflows) {
            if (w.getResultingWorkflow().equals("A")) return true;
        }

        return false;
    }
}
