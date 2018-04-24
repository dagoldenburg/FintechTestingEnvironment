package Tests.FileHandler;

import Tests.Measuring.MeasureResult;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CsvWriter {


    private class Statistics{
        private double bottomConfidenceInterval;
        private double topConfidenceInterval;
        private double average;

        public Statistics(ArrayList<Double> results){

            double total = 0;
            int nrValues = results.size();

            for(Double d : results){
                total += d;
            }

            average = total/results.size();

            ArrayList<Double> preVarians = new ArrayList<>();

            for(Double d : results){
                preVarians.add(new Double((d-average)*(d-average)));
            }

            double totalPreVarians = 0;
            for(Double d : preVarians){
                totalPreVarians += d;
            }

            double varians = totalPreVarians / (nrValues-1);
            double stdAvvikelse = Math.sqrt(varians);

            //Från formeltabell 95% konfidensgrad
            double confValue = 2.093;


            bottomConfidenceInterval = average - (confValue*(stdAvvikelse/(Math.sqrt(nrValues))));
            topConfidenceInterval = average + (confValue*(stdAvvikelse/(Math.sqrt(nrValues))));


        }

        public double getBottomConfidenceInterval() {
            return bottomConfidenceInterval;
        }

        public double getTopConfidenceInterval() {
            return topConfidenceInterval;
        }

        public double getAverage() {
            return average;
        }
    }

    public boolean writeToFile(String fileName, ArrayList<MeasureResult> result){
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(fileName);
            pw.write("###### TEST FILE - " + fileName + "######\n\n\n");
            pw.write("****** CPU USAGE ******\n");
            Statistics stats = getStatistics(result,MeasureType.CPU);
            pw.write("Average: " + stats.getAverage() + "\n");
            pw.write("Confidence interval 1: " + stats.getBottomConfidenceInterval() + "\n");
            pw.write("Confidence interval 2: " + stats.getTopConfidenceInterval() + "\n");
            pw.write("*************************\n\n");

            pw.write("****** RAM USAGE ******\n");
            stats = getStatistics(result,MeasureType.RAM);
            pw.write("Average: " + stats.getAverage() + "\n");
            pw.write("Confidence interval 1: " + stats.getBottomConfidenceInterval() + "\n");
            pw.write("Confidence interval 2: " + stats.getTopConfidenceInterval() + "\n");
            pw.write("***********************\n\n");

            pw.write("****** OPERATION TIME ******\n");
            stats = getStatistics(result,MeasureType.OPERATIONTIME);
            pw.write("Average: " + stats.getAverage() + "\n");
            pw.write("Confidence interval 1: " + stats.getBottomConfidenceInterval() + "\n");
            pw.write("Confidence interval 2: " + stats.getTopConfidenceInterval() + "\n");
            pw.write("*****************************\n\n");



            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }finally{
            if(pw != null){
                pw.close();
            }
        }

    }

    private enum MeasureType{
        OPERATIONTIME, CPU, RAM;
    }

    public Statistics getStatistics(ArrayList<MeasureResult> result, MeasureType measureType){
        ArrayList<Double> list = new ArrayList<>();
        for(MeasureResult d : result){
            switch(measureType){
                case OPERATIONTIME:
                    list.add(d.getOperationTime());
                    break;
                case CPU:
                    list.add(d.getCpuUsage());
                    break;
                case RAM:
                    list.add(d.getRamUsage());
                    break;
                default: break;
            }
        }

        return new Statistics(list);

    }
}
