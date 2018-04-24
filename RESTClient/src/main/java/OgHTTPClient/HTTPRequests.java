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

    public HTTPRequests(String serverIp){
        BASE_URL = "http://" + serverIp + ":7089/rest/";
    }

    /**
     * Authenticates the user to the rest backend.
     */
    public void login(){
        HttpURLConnection conn = null;
        DataOutputStream wr = null;
        BufferedReader br = null;
        try {
            String urlParameters  = "username=dagge&password=dagge";
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            String request        = BASE_URL + "login";
            URL    url            = new URL( request );
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            conn.setUseCaches( false );
            wr = new DataOutputStream( conn.getOutputStream());
            wr.write( postData );
            if (200 <= conn.getResponseCode() && conn.getResponseCode() <= 299) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            myToken = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(conn != null){
                conn.disconnect();
            }
            if(wr != null){
                try {
                    wr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * Makes a transaction to the rest backend.
     */
    public void makeTransaction(){
        HttpURLConnection conn = null;
        DataOutputStream wr = null;
        BufferedReader br = null;
        try {
            String urlParameters  = "usernameTo=dag&usernameFrom=jakob&amount=123.45";
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            String request        = BASE_URL + "makeTransaction";
            URL    url            = new URL( request );
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            conn.setUseCaches( false );
            wr = new DataOutputStream( conn.getOutputStream());
            wr.write( postData );
            if (200 <= conn.getResponseCode() && conn.getResponseCode() <= 299) {

            }else{
                System.out.println("REST MAKE TRANSACTION FAILED!!!!!!");
            }
            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(conn != null){
                conn.disconnect();
            }
            if(wr != null){
                try {
                    wr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Retrieves a transaction based on the input querry
     * @param querry querry to be processed.
     */
    public void retrieveTransaction(String querry) {
        HttpURLConnection conn = null;
        BufferedReader br = null;
        try {
            URL url = new URL(BASE_URL + "getNrOfTransactions?"+querry+"&token="+myToken);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

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

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(conn != null){
                conn.disconnect();
            }
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Removes the user token from the backend.
     */
    public void disconnect(){
        HttpURLConnection conn = null;
        try {
            URL url = new URL(BASE_URL + "removeToken?&token="+myToken);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.disconnect();
            myToken = null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(conn != null){
                conn.disconnect();
            }
        }
    }


}
