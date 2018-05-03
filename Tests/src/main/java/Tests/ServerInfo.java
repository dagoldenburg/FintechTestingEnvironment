package Tests;

public class ServerInfo {
    private final static String serverIp = "10.46.31.225";
    private final static String SFTPUsername = "jakdan";
    private final static String SFTPPassword = "Daggen123";

    public static String getServerIp(){
        return serverIp;
    }

    public static String getSFTPUsername(){
        return SFTPUsername;
    }

    public static String getSFTPPassword(){
        return SFTPPassword;
    }
}
