package bank.impl;

import bank.InactiveException;
import bank.OverdrawException;

public class Account implements bank.Account {
    private String number;
    private String owner;
    private double balance;
    private boolean active = true;

    Account(String owner, String number) {
        // account number has to be set here or has to be passed using
        // the constructor
        this.owner = owner;
        this.number = number;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public boolean isActive() {
        return active;
    }
    
    void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void deposit(double amount) throws InactiveException {
        if (!isActive())
            throw new InactiveException();
        if (amount <= 0)
            throw new RuntimeException(
                    "amount to deposit must be greater than 0");
        balance += amount;
    }

    @Override
    public void withdraw(double amount) throws InactiveException,
            OverdrawException {
        if (!isActive())
            throw new InactiveException();
        if (amount > getBalance())
            throw new OverdrawException();
        balance -= amount;
    }
}