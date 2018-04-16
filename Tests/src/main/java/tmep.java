import com.sun.management.OperatingSystemMXBean;

import javax.management.MBeanServerConnection;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Random;

public class tmep {

    public static void main(String[] args){
        MBeanServerConnection mbsc = ManagementFactory.getPlatformMBeanServer();

        OperatingSystemMXBean osMBean = null;
        try {
            osMBean = ManagementFactory.newPlatformMXBeanProxy(
                    mbsc, ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        long nanoBefore = System.nanoTime();
        long cpuBefore = osMBean.getProcessCpuTime();

// Call an expensive task, or sleep if you are monitoring a remote process

        for(int i = 0;i < 1000000 ;i++){
            System.out.println("xD");
        }

        long cpuAfter = osMBean.getProcessCpuTime();
        long nanoAfter = System.nanoTime();

        long percent;
        if (nanoAfter > nanoBefore)
            percent = ((cpuAfter-cpuBefore)*100L)/
                    (nanoAfter-nanoBefore);
        else percent = 0;

        System.out.println("Cpu usage: "+percent+"%");
    }
}
