package Tests.FileHandler;

import Tests.Measuring.MeasureResult;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CsvWriter {



    public MeasureResult getAverageResult(ArrayList<MeasureResult> results){
        double totalCpu = 0;
        double totalRam = 0;
        double totalOperationTime = 0;

        for(MeasureResult m : results){
            totalCpu += m.getCpuUsage();
            totalRam += m.getRamUsage();
            totalOperationTime += m.getOperationTime();
        }
        int size = results.size();
        return new MeasureResult(totalCpu/size,totalRam/size,totalOperationTime/size);

    }


    public boolean writeToFile(String fileName, ArrayList<MeasureResult> result){
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(fileName);
            pw.write("CPU Usage: ");
            for(MeasureResult r : result){
                pw.write(r.getCpuUsage() +", ");
            }
            pw.write("\n");
            pw.write("Average CPU usage: " + (getAverageResult(result).getCpuUsage()));
            pw.write("\n\n");

            pw.write("RAM Usage: ");
            for(MeasureResult r : result){
                pw.write(r.getRamUsage() +", ");
            }
            pw.write("\n");
            pw.write("Average RAM usage: " + (getAverageResult(result).getRamUsage()));
            pw.write("\n\n");


            pw.write("Operation time: ");
            for(MeasureResult r : result){
                pw.write(r.getOperationTime() +", ");
            }
            pw.write("\n");
            pw.write("Average operation time: " + (getAverageResult(result).getOperationTime()));
            pw.write("\n\n");


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
}
