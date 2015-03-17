package bank.servlet;

import java.io.IOException;

import bank.Bank;
import bank.BankDriver;

public class ServletDriver implements BankDriver {
    
    private Bank bank = new BankProxy();

    @Override
    public void connect(String[] args) throws IOException {
        // not necessary
    }

    @Override
    public void disconnect() throws IOException {
        // not necessary
    }

    @Override
    public Bank getBank() {
        return bank;
    }

}
