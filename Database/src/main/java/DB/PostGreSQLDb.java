package DB;


import DB.Model.Transaction;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the Db interface.
 * This is thought to be used as the primary interface to
 * the database.
 */
public class PostGreSQLDb implements DbI {

    private static String DATABASE_NAME = "ExjobbDatabas";

    Connection connection;

    /**
     * Creates a connection to the PostgreSQL database.
     * @return True if connection could be made, false if not.
     */
    public boolean createConnection() {
        try{
            Log.setPrintLog(true);
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+DATABASE_NAME,
                    "postgres","admin");
            connection.setAutoCommit(true);
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            Log.e(this,"Connection to "+DATABASE_NAME+" was not successful");
            return false;
        }
        Log.i(this,"Connection to "+DATABASE_NAME+" was successful!");
        return true;
    }

    /**
     * Authenticated the user based on username and password.
     * @param username Username to be authenticated.
     * @param password Password for the authentication.
     * @return True if authentication was successful, false if not.
     */
    public boolean authenticateUser(String username, String password) {
        String selectString = "SELECT * FROM users WHERE name=? and password=?;";
        try {
            PreparedStatement ps = connection.prepareStatement(selectString);
            ps.setString(1,username);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            if(!rs.isBeforeFirst()){
                Log.i(this,"Authentication unsuccessful");
                return false;
            }
            Log.i(this,"Authentication successful");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Attempts to store a transaction in the database.
     * @param usernameTo Receiveing user.
     * @param usernameFrom Sending user.
     * @param amount Amount to send.
     * @return True if the transaction could be created, false if not.
     */
    public boolean makeTransaction(String usernameTo,String usernameFrom, double amount) {
        String insertString = "INSERT INTO transactions (amount,fromuser,touser) VALUES(?,?,?);";
        try{
            PreparedStatement ps = connection.prepareStatement(insertString);
            ps.setDouble(1,amount);
            ps.setString(2,usernameFrom);
            ps.setString(3,usernameTo);
            ps.executeUpdate();
        }catch(SQLException e){
            Log.e(this,"Transaction could not be created.");
            return false;
        }
        Log.i(this,"Transaction successfully created!");
        return true;
    }

    /**
     * Retrieves all the transactions in the form of a LinkedList.
     * @param username Username to retrieve transactions for.
     * @return LinkedList with transactions.
     */
    public LinkedList<Transaction> retrieveAllTransactions(String username) {
        String selectString ="SELECT * FROM transactions WHERE touser=?;";
        LinkedList<Transaction> trans = new LinkedList<Transaction>();
        try {
            PreparedStatement ps = connection.prepareStatement(selectString);
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                trans.add(new Transaction(rs.getDouble("amount"),
                        rs.getString("touser"),rs.getString("fromuser"),rs.getInt("transactionid")));
            }
        } catch (SQLException e) {
            Log.e(this,"Could not retrieve transactions");
            e.printStackTrace();
        }
        return trans;
    }


        /**
     * Retrieves X last transactions from a user in the form of a LinkedList
     * @param username
     * @param nrOfTransactions
     * @return returns the last X transactions
     */
    public LinkedList<Transaction> retrieveNrOfTransactions(String username,int nrOfTransactions) {
        String selectString ="SELECT * FROM transactions WHERE fromuser=? ORDER BY transactionid desc LIMIT ?;";
        LinkedList<Transaction> trans = new LinkedList<Transaction>();
        Log.i(this, "Getting nr transactions " + nrOfTransactions);
        try {
            PreparedStatement ps = connection.prepareStatement(selectString);
            ps.setString(1,username);
            ps.setInt(2,nrOfTransactions);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                trans.add(new Transaction(rs.getDouble("amount"),
                        rs.getString("touser"),rs.getString("fromuser"),rs.getInt("transactionid")));
            }
        } catch (SQLException e) {
            Log.e(this,"Could not retrieve transactions");
            e.printStackTrace();
        }
        return trans;
    }


    /**
     * Retrieves all usernames
     * @return A linkedlist of strings with all the usernames available in the database.
     */
    public LinkedList<String> retrieveAllUsernames(){
        String selectString = "SELECT name FROM users";
        LinkedList<String> usernames = new LinkedList<String>();
        try{
            PreparedStatement ps = connection.prepareStatement(selectString);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                usernames.add(new String(rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usernames;
    }

        public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostGreSQLDb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveToken(String token){
        String insertString = "INSERT INTO tokens (token) VALUES (?)";
        PreparedStatement ps;
        try{
            ps = connection.prepareStatement(insertString);
            ps.setString(1,token);
            ps.executeUpdate();
            Log.i(this,"inserted token");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.e(this,"Couldn't insert token");
        }
    }

    public boolean matchToken(String token){
        String selectString = "SELECT * FROM tokens";
        ResultSet rs;
        try{
            PreparedStatement ps = connection.prepareStatement(selectString);
            rs = ps.executeQuery();
            if(rs.next()){
                Log.i(this,"Inserted token");
                return true;
            }else {

                Log.i(this,"No match for token");
                return false;
            }
        } catch (SQLException e) {
            Log.e(this,"Couldn't match token");
            return false;
        }
    }

    public boolean userExists(String username) {
        String searchString = "SELECT * FROM users WHERE name=?;";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(searchString);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostGreSQLDb.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                ps.close();
                rs.close();
            } catch (Exception ex) {
                Logger.getLogger(PostGreSQLDb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String createUser(String username, String password) {
        String createString = "INSERT INTO users (name,password) VALUES(?,?);";
        PreparedStatement ps = null;
        try {
            if (userExists(username)) {
                return "Username already exists!";
            } else {
                ps = connection.prepareStatement(createString);
                ps.setString(1, username);
                ps.setString(2, password);
                ps.execute();
                return "Successfully created account!";
            }

        } catch (SQLException ex) {
            Logger.getLogger(PostGreSQLDb.class.getName()).log(Level.SEVERE, null, ex);
            return "A server error occurred";
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(PostGreSQLDb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
