package OgHTTPClient;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HTTPRequests {

    String myToken;

    private String BASE_URL;

    public void sayTjo() throws IOException {
        /*HttpURLConnection conn = (HttpURLConnection) (url.openConnection());
        conn.setRequestMethod("GET");
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        System.out.println(br.readLine());
        conn.disconnect();*/
    }

    public HTTPRequests(String serverIp){
        BASE_URL = "http://" + serverIp + ":7089/rest/";
    }

    public void login(){
        try {
            String urlParameters  = "username=dagge&password=dagge";
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            String request        = BASE_URL + "login";
            URL    url            = new URL( request );
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            conn.setUseCaches( false );
            try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
                wr.write( postData );
            }
            BufferedReader br;
            if (200 <= conn.getResponseCode() && conn.getResponseCode() <= 299) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            myToken = br.readLine();
            br.close();
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeTransaction(){
        try {
            String urlParameters  = "usernameTo=dag&usernameFrom=jakob&amount=123.45";
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            String request        = BASE_URL + "makeTransaction";
            URL    url            = new URL( request );
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            conn.setUseCaches( false );
            DataOutputStream wr = new DataOutputStream( conn.getOutputStream());
            wr.write( postData );
            BufferedReader br;
            if (200 <= conn.getResponseCode() && conn.getResponseCode() <= 299) {

            }else{
                System.out.println("REST MAKE TRANSACTION FAILED!!!!!!");
            }
            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void retrieveTransaction(String querry) {
        try {
            URL url = new URL(BASE_URL + "getNrOfTransactions?"+querry+"&token="+myToken);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = null;
            try{
                if (200 <= conn.getResponseCode() && conn.getResponseCode() <= 299) {
                    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                } else {
                    System.out.println("FAILED TO RETRIEVE TRANSACTIONS!!!!!!");
                    br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                }
                System.out.println("Response:");
                String s = "";
                while((s = br.readLine()) != null){
                    System.out.println(s);
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                if(br != null){
                    br.close();
                }
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(){
        try {
            URL url = new URL(BASE_URL + "removeToken?&token="+myToken);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.disconnect();
            myToken = null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
