package com.example.controller;

import com.example.dao.CustomerDao;
import com.example.model.Customer;
import com.example.model.Mistake;
import com.example.model.Suggest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Created by root on 19/09/2016.
 */

@RestController
@Transactional
public class DemoController {

    @Autowired
    CustomerDao customerDao;

    @RequestMapping("/save")
    public String process() {
        customerDao.createCustomer(new Customer("Jack", "Smith"));
        customerDao.createCustomer(new Customer("Adam", "Johnson"));
        customerDao.createCustomer(new Customer("Kim", "Smith"));
        customerDao.createCustomer(new Customer("David", "Williams"));
        customerDao.createCustomer(new Customer("Peter", "Davis"));
        return "Done";
    }

    @RequestMapping("/")
    public String hello(){
        return "Hello Hung ";
    }

    @RequestMapping("/findAll")
    public String findAll() {
        List<Customer> list = customerDao.getAllCustomer();
        String result = "<html>";
        for (Customer customer : list) {

            result += " <div>" + customer.toString() + "</div> ";
        }

        return result;
    }

    @CrossOrigin
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public String postHandler(@RequestParam String url) {
        String text = "";

        try {
            //TODO return proper representation object

            Document doc = Jsoup.connect(url).get();
            text = doc.body().text();

        } catch (IOException ex) {

        }
        return customerDao.findMistakePosition(text);
    }



    @RequestMapping("/findbyid")
    public String findById(@RequestParam("id") long id) {
        String result = "";
        result = customerDao.getCustommerById(id).toString();
        return result;
    }

    @RequestMapping("/findbylastname")
    public String fetchDataByLastName(@RequestParam("lastname") String lastName){
        String result = "<html>";

        result = customerDao.getCustomerByLastName(lastName).toString();

        return result + "</html>";
    }

    @CrossOrigin
    @RequestMapping(value = "/mistake", method = RequestMethod.POST)
    public ArrayList<String> findSpellingMistake(@RequestParam String content) {
        String text = "";
        try {
            //TODO return proper representation object
            Document doc = Jsoup.connect(content).get();
            text = doc.body().text();
            System.out.print(text);
        } catch (IOException ex) {

        }
        return customerDao.spellingCheck(text);
    }

    @CrossOrigin
    @RequestMapping(value = "/typing", method = RequestMethod.POST)
    public ArrayList<String> findTypingSpellingMistake(@RequestParam String content) {
        return customerDao.typingCheck(content);
    }

    @CrossOrigin
    @RequestMapping(value = "/spelling", method = RequestMethod.POST)
    public @ResponseBody
    Mistake findParagrahpSpellingMistake(@RequestParam String content) {
        Mistake mistake = new Mistake();
        ArrayList<Suggest> result = customerDao.checkParagraph(content);
        if (result == null) {
            mistake.setMessage("failed");
            mistake.setResult("Sentence must have at least five words");
        } else {
            if (result.size() > 0) {
                mistake.setMessage("success");
                mistake.setResult("found errors");
                mistake.setMistakes(result);
            } else {
                mistake.setMessage("success");
                mistake.setResult("not found errors");
                mistake.setMistakes(result);
            }
        }

        return mistake;
    }

    @RequestMapping(value = "/position", method = RequestMethod.POST)
    public String findPositionMistake(@RequestParam String content) {
        return customerDao.positionMistake(content);
    }
}
