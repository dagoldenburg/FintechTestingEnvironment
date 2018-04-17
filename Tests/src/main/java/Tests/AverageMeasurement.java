package Tests;

import com.sun.management.OperatingSystemMXBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
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

    public AverageMeasurement(String folderName,String csvFileName) {
        this.csvFile = new File(csvFileName);
        this.folderName = folderName;

        this.rt = Runtime.getRuntime();
        totalCpuUsage = 0;
        totalRamUsage = 0;
        totalMeasurements = 0;
    }

    public void startTest() throws IOException {
        this.osMBean = ManagementFactory.newPlatformMXBeanProxy(
                ManagementFactory.getPlatformMBeanServer(),
                ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME,
                OperatingSystemMXBean.class);
        startTime = System.currentTimeMillis();
    }

    public void stopTest() throws FileNotFoundException {
        stopTime = System.currentTimeMillis();
        PrintWriter pw = new PrintWriter("TestResults/" + folderName + "/" +csvFile+ ".csv");
        pw.write("Average CPU usage: " + (totalCpuUsage/totalMeasurements));
        pw.write("\n");
        pw.write("Average RAM usage: " + (totalRamUsage/totalMeasurements));
        pw.write("\n");
        pw.write("Operational Time: "+(stopTime-startTime));
        pw.close();
    }


    public void run() {
        try {
            startTest();
            try {
                while (true) {
                    totalMeasurements++;
                    totalCpuUsage += osMBean.getProcessCpuLoad();
                    totalRamUsage += (
                            ((rt.totalMemory() - rt.freeMemory())
                                    /((double)1600000000*10))*100);
                    Thread.sleep(10);
                }
            }catch(InterruptedException e){
                stopTest();
                Thread.currentThread().interrupt();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
