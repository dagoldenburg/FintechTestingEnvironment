package SFTPLogic;

import Exceptions.SFTPClientException;
import com.jcraft.jsch.*;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Properties;

public class SFTPClient implements SFTPClientI{

    private String ip;
    private int port;
    private int tcpPort;
    private String username;
    private String password;

    public SFTPClient(String ip, int port, String username, String password,int tcpPort) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
        this.tcpPort = tcpPort;

    }

    private JSch jsch;
    private Session session;
    private Channel channel;
    private ChannelSftp c;

    @Override
    public void connect() throws SFTPClientException{
        jsch = new JSch();
        try {
            session = jsch.getSession(username,ip,port);
            session.setPassword(password.getBytes(Charset.forName("ISO-8859-1")));

            Properties config = new Properties();
            config.put("StrictHostKeyChecking","No");
            session.setConfig(config);

            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            c = (ChannelSftp) channel;

           // System.out.println("CONNECTED TO SFTP SERVER "+ip+":"+port+" AS "+username);
        } catch (JSchException e) {
            e.printStackTrace();
            throw new SFTPClientException(e.getMessage());
        }
    }

    @Override
    public void uploadFile(String sourceFileName,String destFileName) throws SFTPClientException {
        try{
            if(c==null)
                throw new SFTPClientException("Not connected");
            c.put(sourceFileName,destFileName);
        }catch(SftpException e){
            throw new SFTPClientException(e.getMessage());
        }
    }

    public void retrieveFile(String receivingUser, int amountOfTransactions){
        BufferedReader inFromServer = null;
        DataOutputStream outToServer = null;
        Socket clientSocket = null;
        try {
            clientSocket = new Socket(ip, tcpPort);
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outToServer.writeBytes("CF " + receivingUser + " " + amountOfTransactions + "\n");
            String response = inFromServer.readLine();
            String[] strings = response.split(" ");
            if(strings[0].equals("DONE")){
                retrieveFile("/Users/do/Documents/RESPONSEDOCUMENTS/" + strings[1]+".txt",
                        "/Users/do/IdeaProjects/ExjobbMonkaS/FileStatioN/"+strings[1]+".txt");
                //System.out.println("Fuckin goteeem");
                printResult("/Users/do/IdeaProjects/ExjobbMonkaS/FileStatioN/"+strings[1]+".txt");
            }else{
                System.out.println("FAILED TO RETRIEVE SFTP FILE!!!!!!");
            }
            clientSocket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SFTPClientException e) {
            e.printStackTrace();
        } finally{
            if(inFromServer != null){
                try {
                    inFromServer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(outToServer != null){
                try {
                    outToServer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(clientSocket != null){
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void retrieveFile(String sourceFileName,String destFileName) throws SFTPClientException {
        try {
            if(c==null)
                 throw new SFTPClientException("Not connected");
            c.get(sourceFileName, destFileName);

        } catch (SftpException e) {
            e.printStackTrace();
            throw new SFTPClientException(e.getMessage());
        }
    }

    private void printResult(String filename){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filename));
            String line = br.readLine();
            int i = 0;
            while(line != null){
                System.out.println(line);
                line = br.readLine();
                i++;
            }
            System.out.println("Length: " + i);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void disconnect() {
        if(c!=null)
            c.disconnect();

        if(channel!=null)
            channel.disconnect();
        if(session!=null)
            session.disconnect();
       // System.out.println("Disconnected");
    }

}
