package Tests.Measuring;

public class MeasureResult {
    private double cpuUsage;
    private double ramUsage;
    private double operationTime;

    public MeasureResult(double cpuUsage, double ramUsage, double operationTime){
        this.cpuUsage = cpuUsage;
        this.ramUsage = ramUsage;
        this.operationTime = operationTime;
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public double getRamUsage() {
        return ramUsage;
    }

    public void setRamUsage(double ramUsage) {
        this.ramUsage = ramUsage;
    }

    public double getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(double operationTime) {
        this.operationTime = operationTime;
    }
}
