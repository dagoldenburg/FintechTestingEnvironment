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
import java.util.concurrent.atomic.AtomicBoolean;

public class AverageMeasurement implements Runnable {

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

    private final AtomicBoolean isRunning;


    public AverageMeasurement() {

        this.rt = Runtime.getRuntime();
        totalCpuUsage = 0;
        totalRamUsage = 0;
        totalMeasurements = 0;
        cpuUsageList = new ArrayList<>();
        ramUsageList = new ArrayList<>();
        isRunning = new AtomicBoolean(false);
    }

    public void startTest() throws IOException {
        this.osMBean = ManagementFactory.newPlatformMXBeanProxy(
                ManagementFactory.getPlatformMBeanServer(),
                ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME,
                OperatingSystemMXBean.class);
        startTime = System.currentTimeMillis();

    }

    public void stopTest(){
        stopTime = System.currentTimeMillis();
        result = new MeasureResult(getAverage(cpuUsageList),getAverage(ramUsageList),(stopTime-startTime));
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
            while (isRunning.get()) {
                cpuUsageList.add(osMBean.getProcessCpuLoad()*100);
                ramUsageList.add((rt.totalMemory() - (double)rt.freeMemory())/1000);
              //  ramUsageList.add((
                    //    ((rt.totalMemory() - rt.freeMemory())
                               // /((double)1600000000*10))*100));
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
        this.isRunning.set(isRunning);
    }
}
