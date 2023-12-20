import java.util.ArrayList;
import java.util.Arrays;

public class Module {
    private ArrayList<String> outputs;
    private String name;
    private String type;

    // flip flop boolean
    private boolean on;

    // conjunction input list
    private ArrayList<String> inputs;

    public Module(String data) {
        on = false;
        outputs = new ArrayList<String>();
        inputs = new ArrayList<String>();
        if (data.indexOf("broadcaster") != -1) {
            name = "broadcaster";
            type = "b";
        }
        else {
            int space = data.indexOf(" ");
            name = data.substring(1, space);
            type = data.charAt(0) + "";
        }

        int arrow = data.indexOf(">");
        String outputLine = data.substring(arrow+1);
        String[] outputArray = outputLine.split(",");
        for (String o : outputArray) {
            outputs.add(o.trim());
        }
    }

    public String toString() {
        return type + " " + name + " Outputs: " + outputs + " Inputs: " + inputs;
    }

    public ArrayList<String> getOutputs() {
        return outputs;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isOn() {
        return on;
    }

    public ArrayList<String> getInputs() {
        return inputs;
    }

    public void setInputs(ArrayList<String> inputs) {
        this.inputs = inputs;
    }

    public void sendBroadcasterPulse(ArrayList<Pulse> pulses) {
        for (String o : outputs) {
            String source = this.name;
            String destination = o;
            int type = 0;
            Pulse p = new Pulse(source, destination, type);
            pulses.add(p);
        }
    }

    public void processPulse(Pulse p, ArrayList<Pulse> pulses) {

        // flip flop
        // if pulse is high, do nothing
        // if pulse is low, switch state
            // if on, send high pulse to destination list
            // if off, send low pulse to destination list

        if (type.equals("%")) {
            if (p.getType() == 0) {
                on = !on;
                int pulseType = -1;

                if (on)
                    pulseType = 1;
                else
                    pulseType = 0;

                for (String o : outputs) {
                    String source = this.name;
                    String destination = o;
                    Pulse newPulse = new Pulse(source, destination, pulseType);
                    pulses.add(newPulse);
                }
            }
        }

        // conjunction module
        // update inputList with this source and its pulse type
        // if all inputList pulses are high, send low to all destinations
        // otherwise, send high to all destinations
        if (type.equals("&")) {
            for (int i = 0; i < inputs.size(); i++) {
                if (inputs.get(i).indexOf(p.getSource()) == 0) {
                    inputs.set(i, p.getSource()+"-"+p.getType());
                }
            }

            boolean allHigh = true;
            for (int i = 0; i < inputs.size(); i++) {
                if (inputs.get(i).charAt(inputs.get(i).length()-1) == '0')
                    allHigh = false;
            }
            int pulseType = -1;
            if (allHigh)
                pulseType = 0;
            else
                pulseType = 1;

            for (String o : outputs) {
                String source = this.name;
                String destination = o;
                Pulse newPulse = new Pulse(source, destination, pulseType);
                pulses.add(newPulse);
            }
        }

        p.setProcessed(true);
    }
}
