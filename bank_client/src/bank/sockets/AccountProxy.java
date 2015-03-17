package bank.sockets;

import java.io.IOException;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;

public class AccountProxy implements Account {

    @Override
    public String getNumber() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getOwner() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isActive() throws IOException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void deposit(double amount) throws IOException,
            IllegalArgumentException, InactiveException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void withdraw(double amount) throws IOException,
            IllegalArgumentException, OverdrawException, InactiveException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public double getBalance() throws IOException {
        // TODO Auto-generated method stub
        return 0;
    }

}
