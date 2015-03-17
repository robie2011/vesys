package bank.servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bank.communication.Request;
import bank.impl.Bank;

public class BankServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static Bank bank = new Bank();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Request request = null;

        ObjectOutputStream oos = new ObjectOutputStream(resp.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(req.getInputStream());
        
        try {
            request = (Request) ois.readObject();
            request.handleRequest(bank);
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        if(request != null) {
            oos.writeObject(request);
            resp.setStatus(200);
        }
    }
}
