package DB;

import DB.Model.Transaction;
import java.util.List;

public interface DbI {

    boolean createConnection();
    boolean authenticateUser(String usernameTo, String password);
    boolean makeTransaction(String usernameTo, String usernameFrom, double amount);
    List<Transaction> retrieveAllTransactions(String username);
    List<Transaction> retrieveNrOfTransactions(String username, int nrOfTransactions);
    List<String> retrieveAllUsernames();
    public void disconnect();
    public String createUser(String username, String password);
    public boolean userExists(String username);
    public void saveToken(String token);
    public boolean matchToken(String token);
}
