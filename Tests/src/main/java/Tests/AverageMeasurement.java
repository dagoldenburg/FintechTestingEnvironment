package Tests;

import Tests.Measuring.MeasureResult;
import com.sun.management.OperatingSystemMXBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AverageMeasurement implements Runnable {
    private File csvFile;
    private String folderName;

    private Runtime rt;
    private OperatingSystemMXBean osMBean;
    private long startTime;
    private long stopTime;

    private double totalCpuUsage;
    private double totalRamUsage;
    private int totalMeasurements;


    private ArrayList<Double> cpuUsageList;
    private ArrayList<Double> ramUsageList;

    private MeasureResult result;

    private boolean isRunning;


    public AverageMeasurement(String folderName,String csvFileName) {
        this.csvFile = new File(csvFileName);
        this.folderName = folderName;

        this.rt = Runtime.getRuntime();
        totalCpuUsage = 0;
        totalRamUsage = 0;
        totalMeasurements = 0;
        cpuUsageList = new ArrayList<>();
        ramUsageList = new ArrayList<>();
        isRunning = false;
    }

    public void startTest() throws IOException {
        this.osMBean = ManagementFactory.newPlatformMXBeanProxy(
                ManagementFactory.getPlatformMBeanServer(),
                ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME,
                OperatingSystemMXBean.class);
        startTime = System.currentTimeMillis();
        isRunning = true;
    }

    public void stopTest(){
        System.out.println("Stopping test");
        stopTime = System.currentTimeMillis();

        result = new MeasureResult(getAverage(cpuUsageList),getAverage(ramUsageList),(stopTime-startTime));
/*
        PrintWriter pw = new PrintWriter("TestResults/" + folderName + "/" +csvFile+ ".csv");
        pw.write("Average CPU usage: " + (totalCpuUsage/totalMeasurements));
        pw.write("\n");
        pw.write("Average RAM usage: " + (totalRamUsage/totalMeasurements));
        pw.write("\n");
        pw.write("Operational Time: "+(stopTime-startTime));
        pw.close();*/
    }

    public double getAverage(ArrayList<Double> list){
        double total = 0;

        for(Double d : list){
            total += d;
        }

        return total/list.size();
    }

    public void run() {
        try {
            startTest();
            while (isRunning) {
                cpuUsageList.add(osMBean.getProcessCpuLoad());
                ramUsageList.add((
                        ((rt.totalMemory() - rt.freeMemory())
                                /((double)1600000000*10))*100));
                try{
                    Thread.sleep(10);
                }catch(InterruptedException e){
                    break;
                }
            }
            stopTest();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MeasureResult getResult(){
        return result;
    }

    public void setIsRunning(boolean isRunning){
        this.isRunning = isRunning;
    }
}
