import com.sun.management.OperatingSystemMXBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.util.Locale;

public class Measurement implements Runnable{

    private StringBuilder CPUsb,RAMsb,TIMEsb;
    private File csvFile;

    private Runtime rt;
    private OperatingSystemMXBean osMBean;
    private long startTime;
    private long stopTime;

    public Measurement(String csvFileName) {
        this.CPUsb = new StringBuilder();
        this.RAMsb = new StringBuilder();
        this.TIMEsb = new StringBuilder();
        this.csvFile = new File(csvFileName);
        this.rt = Runtime.getRuntime();
    }

    public void startTest() throws IOException {
        this.osMBean = ManagementFactory.newPlatformMXBeanProxy(
                ManagementFactory.getPlatformMBeanServer(),
                ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME,
                OperatingSystemMXBean.class);
        CPUsb.append("CPUProcLoad,");
        RAMsb.append("RAMCons,");
        TIMEsb.append("Time(ms),");
        startTime = System.currentTimeMillis();
    }

    public void stopTest() throws FileNotFoundException {
        stopTime = System.currentTimeMillis();
        CPUsb.append('\n');
        RAMsb.append('\n');
        TIMEsb.append('\n');
        PrintWriter pw = new PrintWriter(csvFile+".csv");
        pw.write(CPUsb.toString());
        pw.write(RAMsb.toString());
        pw.write(TIMEsb.toString());
        pw.write("Operational Time,"+(stopTime-startTime));
        pw.close();
    }


    public void run() {
        try {
            startTest();
            long firstTime = System.currentTimeMillis();
            try {
                while (true) {
                    Thread.sleep(10);
                    CPUsb.append(String.format(Locale.US, "%1.8f", osMBean.getProcessCpuLoad()));
                    CPUsb.append(",");
                    RAMsb.append(((rt.totalMemory() - rt.freeMemory()) / 1024));
                    RAMsb.append(",");
                    TIMEsb.append(System.currentTimeMillis()-firstTime);
                    TIMEsb.append(",");
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
