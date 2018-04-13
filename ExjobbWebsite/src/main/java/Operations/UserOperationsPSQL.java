/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Operations;

import DB.DbI;
import DB.Log;
import DB.Model.Transaction;
import DB.PostGreSQLDb;
import ViewModel.ViewTransaction;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author jakdan
 */
public class UserOperationsPSQL implements UserOperations {

    @Override
    public boolean login(String username, String password) {
        DbI dbReference = new PostGreSQLDb();
        try {
            if (dbReference.createConnection()) {
                return dbReference.authenticateUser(username, password);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            dbReference.disconnect();
        }
        return false;
    }

    @Override
    public boolean userExists(String username) {
        DbI dbReference = new PostGreSQLDb();
        boolean ret = false;
        if (dbReference.createConnection()) {
            ret = dbReference.userExists(username);
        }
        dbReference.disconnect();
        return ret;
    }

    @Override
    public String createUser(String username, String password) {
        DbI dbReference = new PostGreSQLDb();
        String ret = "";
        if (dbReference.createConnection()) {
            ret = dbReference.createUser(username, password);
        } else {
            ret = "Failed to create database connection";
        }
        dbReference.disconnect();
        return ret;
    }

    @Override
    public String createTransaction(String from, String to, double amount) {
        DbI dbReference = new PostGreSQLDb();
        String ret = "";
        if (dbReference.createConnection()) {
            if(dbReference.makeTransaction(to, from, amount)){
                ret = "Transaction successfully created";
            }else{
                ret = "Failed to create transaction";
            }
        } else {
            ret = "Failed to create database connection";
        }
        dbReference.disconnect();
        return ret;
    }

    @Override
    public List<ViewTransaction> getTransactions(String username) {
        DbI dbReference = new PostGreSQLDb();
        List<ViewTransaction> viewTxs = new ArrayList<>();
        
        try {
            if (dbReference.createConnection()) {
                List<Transaction> txs = dbReference.retrieveAllTransactions(username);
                Log.i(this, "Retreived transactions: " + txs.size());
                for (Transaction t : txs) {
                    ViewTransaction vt = new ViewTransaction(t.getAmount(), t.getTo(), t.getFrom(), t.getTransactionId());
                    viewTxs.add(vt);
                }
            }
        } catch (Exception e) {
            Log.i(e, username);
        }finally{
            dbReference.disconnect();
        }
        return viewTxs;
    }

    @Override
    public List<ViewTransaction> retrieveNrOfTransactions(String username, int nrOfTransactions) {
        DbI dbReference = new PostGreSQLDb();
        List<ViewTransaction> viewTxs = new ArrayList<>();

        Log.i(this, "retrieving nr of transactions");
        try {
            if (dbReference.createConnection()) {
                Log.i(this, "Trying to get " + nrOfTransactions + " transactions for user " + username);
                LinkedList<Transaction> txs = (LinkedList) dbReference.retrieveNrOfTransactions(username, nrOfTransactions);
                Log.i(this, "Retreived transactions: " + txs.size());
                for (Transaction t : txs) {
                    ViewTransaction vt = new ViewTransaction(t.getAmount(), t.getTo(), t.getFrom(), t.getTransactionId());
                    viewTxs.add(vt);
                }
                for(int i = 0; i < viewTxs.size(); i++){
                    viewTxs.get(i).setId(viewTxs.size()-i);
                }
            }
        } catch (Exception e) {
            Log.i(e, username);
        }finally{
            dbReference.disconnect();
        }
        return viewTxs;
    }

}
