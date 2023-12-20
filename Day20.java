import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class Day20 {
    public static void main(String[] args) {
        ArrayList<String> fileData = getFileData("data/Day20_Input");

        // Module class --> list of output modules, name
        //              --> method to send a pulse to a module

        // FlipFlop class (extends Module). Has an on or off status

        // Conjunction class (extends Module). Has a list of all module names that send to this module
        //                                     Keep track of most recent pulse sent by each input module

        // Arraylist to hold all sent pulses

        ArrayList<Module> modules = new ArrayList<Module>();
        ArrayList<Pulse> pulses = new ArrayList<Pulse>();
        for (String d : fileData) {
            Module m = new Module(d);
            modules.add(m);
        }




        for (int i = 0; i < modules.size(); i++) {
            Module current = modules.get(i);
            ArrayList<String> inputList = new ArrayList<String>();
            if (current.getType().equals("&")) {
                String name = current.getName();
                for (String line : fileData) {
                    if (line.indexOf(name) != 1 && line.indexOf(name) != -1) {
                        //System.out.println(name + " input from: " + line.substring(1, line.indexOf(" ")));
                        String iName = line.substring(1, line.indexOf(" "));
                        inputList.add(iName + "-0");
                    }
                }
            }
            current.setInputs(inputList);
        }


        // part 2 stuff
        int rxIndex = findRXIndex(modules);
        Module m = modules.get(rxIndex);
        ArrayList<String> importantOutputs = m.getInputs();

        boolean[] rxInputs = new boolean[importantOutputs.size()];
        String[] rxInputNames = new String[importantOutputs.size()];
        long[] rxIterations = new long[importantOutputs.size()];

        for (int j = 0; j < importantOutputs.size(); j++) {
            rxInputNames[j] = importantOutputs.get(j).substring(0, importantOutputs.get(j).indexOf("-"));
        }

        long lowTotal = 0;
        long highTotal = 0;
        int i = 1;
        while (true) {
            pulses = new ArrayList<Pulse>();
            int broadCasterIndex = findBroadcasterIndex(modules);
            modules.get(broadCasterIndex).sendBroadcasterPulse(pulses);

            int processIndex = findLowestNonProcessedPulse(pulses);
            while (processIndex != -1) {

                Pulse process = pulses.get(processIndex);
                int moduleIndex = findModuleIndex(modules, process.getDestination());
                if (moduleIndex != -1) {
                    modules.get(moduleIndex).processPulse(process, pulses);
                }
                else {
                    process.setProcessed(true);
                }
                processIndex = findLowestNonProcessedPulse(pulses);
            }

            for (int x = 0; x < rxInputNames.length; x++) {
                if (!rxInputs[x]) {
                    if (checkLowPulse(rxInputNames[x], pulses)) {
                        rxInputs[x] = true;
                        rxIterations[x] = i;
                    }
                }
            }

            boolean allFound = true;
            for (boolean c : rxInputs) {
                if (!c)
                    allFound = false;
            }

            if (allFound) {
                break;
            }

            long low = 1;
            long high = 0;
            for (Pulse p : pulses) {
                if (p.getType() == 0) low++;
                else high++;
            }
            lowTotal += low;
            highTotal += high;
            i++;

        }

        // part 1 answer is lowTotal * highTotal
        findLCM(rxIterations);

    }

    public static boolean checkLowPulse(String moduleName, ArrayList<Pulse> pulses) {
        for (Pulse p : pulses) {
            if (p.getDestination().equals(moduleName) && p.getType() == 0) {
                return true;
            }
        }
        return false;
    }

    public static int findModuleIndex(ArrayList<Module> modules, String name) {
        for (int i = 0; i < modules.size(); i++) {
            if (modules.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public static int findRXIndex(ArrayList<Module> modules) {
        for (int i = 0; i < modules.size(); i++) {
                Module m = modules.get(i);
                ArrayList<String> outputs = m.getOutputs();
                for (String o : outputs) {
                    if (o.equals("rx"))
                        return i;
            }
        }
        return -1;
    }

    public static int findLowestNonProcessedPulse(ArrayList<Pulse> pulses) {
        for (int i = 0; i < pulses.size(); i++) {
            if (!pulses.get(i).isProcessed())
                return i;
        }
        return -1;
    }

    public static int findBroadcasterIndex(ArrayList<Module> moduleList) {
        for (int i = 0; i < moduleList.size(); i++) {
            if (moduleList.get(i).getName().equals("broadcaster")) {
                return i;
            }
        }
        return -1;
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

    // findGCD and findLCM taken from the internets to find the Lowest Common Multiple
    // of an array of numbers (this is built-in for python!)
    public static long findGCD(long a, long b){
        //base condition
        if(b == 0)
            return a;

        return findGCD(b, a%b);
    }

    public static void findLCM(long[] array) {
        //initialize LCM and GCD with the first element
        long lcm = array[0];
        long gcd = array[0];

        //loop through the array to find GCD
        //use GCD to find the LCM
        for(int i=1; i<array.length; i++){
            gcd = findGCD(array[i], lcm);
            lcm = (lcm*array[i])/gcd;
        }

        //output the LCM
        System.out.println(lcm);
    }
}
