/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Operations;

import ViewModel.ViewTransaction;
import java.util.List;

/**
 *
 * @author jakdan
 */
public interface UserOperations {
    public boolean login(String username, String password);
    public boolean userExists(String username);
    public String createUser(String username, String password);
    public String createTransaction(String from, String to, double amount);
    public List<ViewTransaction> getTransactions(String username);
    public List<ViewTransaction> retrieveNrOfTransactions(String username, int nrOfTransactions);
}
