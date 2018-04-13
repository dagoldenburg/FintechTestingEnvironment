import DB.PostGreSQLDb;
import DB.DbI;
import DB.Model.Transaction;

import java.util.Scanner;


public class Main {

    public static void main(String[] args){

        DbI dbReference = new PostGreSQLDb();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write username:");
        String username = scanner.nextLine();
        System.out.println("Write password");
        String password = scanner.nextLine();
        dbReference.createConnection();
        dbReference.authenticateUser(username,password);
        while(true){
            System.out.println("1. make transaction");
            System.out.println("2. get transactions");
            if(scanner.nextLine().equals("1")){
                System.out.print("to: ");
                String to = scanner.nextLine();
                System.out.print("amount: ");
                String amount = scanner.nextLine();
                System.out.println(dbReference.makeTransaction(to,username,Double.parseDouble(amount)));
            }else{
                System.out.print("get transactions for username: ");
                String target = scanner.nextLine();
                for(Transaction t: dbReference.retrieveAllTransactions(target)){
                    System.out.println(t.toString());
                }
            }

        }
    }

}
