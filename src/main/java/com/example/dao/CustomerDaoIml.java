package com.example.dao;

import com.example.model.Customer;
import org.springframework.stereotype.Repository;
import spellcheck.SpellChecker;
import spellcheck.SpellcheckUI;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by root on 19/09/2016.
 */
@Repository
public class CustomerDaoIml implements CustomerDao {

    public SpellChecker spellChecker;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Customer> getAllCustomer() {
        String sql = "SELECT c FROM Customer c";
        TypedQuery<Customer> query = entityManager.createQuery(sql, Customer.class);
        return query.getResultList();
    }

    @Override
    public Customer getCustommerById(long id) {
        String sql = "SELECT c FROM Customer c WHERE c.id = :id";
        TypedQuery<Customer> query = entityManager.createQuery(sql, Customer.class).setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public Customer getCustomerByLastName(String name) {
        String sql = "SELECT c FROM Customer c WHERE c.lastName = :lastName";
        TypedQuery<Customer> query = entityManager.createQuery(sql, Customer.class).setParameter("lastName",name);
        return query.getSingleResult();
    }

    @Override
    public void createCustomer(Customer customer) {
        entityManager.persist(customer);
    }

    @Override
    public String positionMistake(String content) {
        try {
            spellChecker = new SpellChecker("/home/hoangnhat/Desktop/ngramdict/");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO add your handling code here:
        //String inputText = this.jTextArea1.getText();
        String output = "";
        double runningTime = 0.0;
        if (content.length() > 0) {
            //System.out.println(inputText);
            String[] lines = content.split("\n");
//            System.out.println("lines length " + lines.length);
            String result = "";
            for (int i = 0; i < lines.length; i++) {
//                System.out.println(lines[0]);
                try {
                    long start = System.currentTimeMillis();
                    //lines[i] = res.restoration(lines[i]);
//                    if(this.jRadioButton2.isSelected()){
//                        lines[i] = res.restoration(lines[i]);
//                    }
                    result += spellChecker.processLine(lines[i]) + "\n";
                    long end = System.currentTimeMillis();
                    runningTime = (end - start)*1.0/1000;
                } catch (Exception ex) {
                    Logger.getLogger(SpellcheckUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            output = result + " - " + runningTime;
            //System.out.println(result);
        } else {
            output = "empty input";
        }

        return output;
    }

    @Override
    public ArrayList<String> spellingCheck(String content) {
        ArrayList<String> result = new ArrayList<>();

        try {
            spellChecker = new SpellChecker("/home/hoangnhat/Desktop/ngramdict/");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO add your handling code here:
        //String inputText = this.jTextArea1.getText();
        String output = "";
        double runningTime = 0.0;
        if (content.length() > 0) {
            //System.out.println(inputText);
            String[] lines = content.split("\n");
//            System.out.println("lines length " + lines.length);
//            String result = "";

            for (int i = 0; i < lines.length; i++) {
//                System.out.println(lines[0]);
                try {
                    long start = System.currentTimeMillis();
                    //lines[i] = res.restoration(lines[i]);
//                    if(this.jRadioButton2.isSelected()){
//                        lines[i] = res.restoration(lines[i]);
//                    }
                    result = spellChecker.processSentence(lines[i]);
                    long end = System.currentTimeMillis();
//                    runningTime = (end - start)*1.0/1000;
                } catch (Exception ex) {
                    Logger.getLogger(SpellcheckUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
//            output = result;
            //System.out.println(result);
        } else {
//            output = "empty input";
        }

        return result;
    }
}
