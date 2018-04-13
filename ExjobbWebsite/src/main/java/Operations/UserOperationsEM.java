/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Operations;


import Model.BankUser;
import ViewModel.ViewTransaction;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Jakob
 */
public class UserOperationsEM implements UserOperations{

    private final static String PERSISTENCE_NAME = "FrontendPU";


    public boolean login(String username, String password) {
        
        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_NAME);
        em = emf.createEntityManager();

        try {
            Query q = em.createQuery(
                    "SELECT u FROM BankUser u WHERE u.username LIKE :username AND u.password LIKE :password");
            q.setParameter("username", username);
            q.setParameter("password", password);
            q.setMaxResults(1);
            q.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }


    public boolean userExists(String username) {

        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_NAME);
        em = emf.createEntityManager();

        try {
            em.createQuery(
                    "SELECT u FROM BankUser u WHERE u.username LIKE :username")
                    .setParameter("username", username)
                    .setMaxResults(1)
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }

    public String createUser(String username, String password) {

        EntityManager em;
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_NAME);
        em = emf.createEntityManager();

        try {
            if (!userExists(username)) {

                em.getTransaction().begin();
                BankUser userToInsert = new BankUser(username, password);
                em.persist(userToInsert);
                em.flush();
                em.getTransaction().commit();
                return "Successfully created account!";
            } else {
                return "Username already exists!";
            }
        } catch (Exception e) {
            return "A server error occurred: " + e.toString();
        } finally {
            if (em != null) {
                em.close();
            }
            emf.close();

        }

    }

    @Override
    public String createTransaction(String from, String to, double amount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ViewTransaction> getTransactions(String username) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ViewTransaction> retrieveNrOfTransactions(String username, int nrOfTransactions) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

