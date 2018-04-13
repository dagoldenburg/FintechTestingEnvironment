/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagedBeans;

import Operations.UserOperations;
import Operations.UserOperationsPSQL;
import ViewModel.ViewTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Jakob
 */
@ManagedBean
@SessionScoped
public class UserBean implements Serializable {

    UserOperations userHelper;
    private String username;
    private String password;
    private Boolean authorized;
    private String statusMessage;
    private String password2;

    private String to;
    private String amount;

    
    private String nrTransactions;
    
    public Boolean isLoggedInUser(String username) {
        return this.username.equals(username);
    }

    
    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public UserBean() {
        nrTransactions = "0";
        to = "";
        amount = "";
        authorized = false;
        userHelper = new UserOperationsPSQL(); //CHANGE FOR ANOTHER IMPLEMENTATION
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String redirectLoginPage() {
        return "login.xhtml";
    }

    public Boolean getIsLoggedIn() {
        return authorized;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public String logout() {
        username = "";
        password = "";
        authorized = false;
        return "login.xhtml";
    }

    
    
    
    public String doLogin() {
        statusMessage = "";
        if (userHelper.login(username, password)) {
            authorized = true;
            return "index.xhtml";
        } else {
            statusMessage = "Wrong password or username";
            return "login.xhtml";
        }
    }

    public void createAccount() {
        statusMessage = "";

        if (username.length() < 3 || username.length() > 15) {
            statusMessage = "Username length must be between 3 and 15 characters";
            return;
        }

        if (!password.equals(password2)) {
            statusMessage = "Passwords doesn't match!";
            return;
        }

        if ((password.length() < 5) || (password.length() > 20)) {
            statusMessage = "Password length must be between 5 and 20 characters";
            return;
        }

        try {
            statusMessage = userHelper.createUser(username, password);
            username = "";
        } catch (Exception e) {
            statusMessage = e.toString();
        }
    }

    public String nameAlreadyExists() {
        if (username != null && !username.equals("")) {
            if (userHelper.userExists(username)) {
                return "Username already exists!";
            } else {
                return "Username is available";
            }
        } else {
            return "";
        }
    }

    public void createTransaction() {

        try {
            if (getTo().equals("")) {
                statusMessage = "Please enter a username";
                return;
            }
            double d = Double.parseDouble(getAmount());
            if (d <= 0) {
                throw new Exception();
            }
            statusMessage = userHelper.createTransaction(username, getTo(), d);
        } catch (Exception e) {
            statusMessage = "Please enter a valid amount";
        } finally {
            to = "";
            amount = "";
        }
    }

    public List<ViewTransaction> getTransactions() {
        statusMessage = "";
        return userHelper.getTransactions(username);
    }

    public List<ViewTransaction> getNrOfTransactions(){
        statusMessage = "";
        try{
            int intNrTransactions = Integer.parseInt(nrTransactions);
            return userHelper.retrieveNrOfTransactions(username, intNrTransactions);
        }catch(Exception e){
            return new ArrayList<ViewTransaction>();
        }
    }
    
    /**
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * @return the nrTransactions
     */
    public String getNrTransactions(){
        return this.nrTransactions;
    }

    /**
     * @param nrTransactions the nrTransactions to set
     */
    public void setNrTransactions(String nrTransactions) {
        this.nrTransactions = nrTransactions;
    }

}
