package bank.sockets;

import java.io.IOException;
import java.util.Set;

import bank.Account;
import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;

public class BankProxy implements Bank {

    @Override
    public String createAccount(String owner) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean closeAccount(String number) throws IOException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Set<String> getAccountNumbers() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Account getAccount(String number) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void transfer(Account a, Account b, double amount)
            throws IOException, IllegalArgumentException, OverdrawException,
            InactiveException {
        // TODO Auto-generated method stub
        
    }

}
