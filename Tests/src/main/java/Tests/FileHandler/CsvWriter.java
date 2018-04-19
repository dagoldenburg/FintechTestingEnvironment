package Tests.FileHandler;

import Tests.Measuring.MeasureResult;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CsvWriter {


    public boolean writeToFile(String fileName, MeasureResult result){
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(fileName);
            pw.write("Average CPU usage: " + (result.getCpuUsage()));
            pw.write("\n");
            pw.write("Average RAM usage: " + (result.getRamUsage()));
            pw.write("\n");
            pw.write("Operational Time: "+(result.getOperationTime()));
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
