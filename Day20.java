import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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
            if (checkLowPulse("bg", pulses)) {
                System.out.println(i);
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

//        kz --> rx
//
//        sj --> kz
//        qq --> kz
//        ls --> kz
//        bg --> kz

        // I figured this out manually .... dumb.

        // rx will receive a low pulse when kz gets all high pulses
        // kz will have all high pulses when sj, qq, ls, and bg have low
        // when sj, qq, ls and bg have low pulse in the same iteration .. that's the answer?


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
}
