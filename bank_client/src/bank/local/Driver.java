/*
 * Copyright (c) 2000-2015 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package bank.local;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import bank.InactiveException;
import bank.OverdrawException;

public class Driver implements bank.BankDriver {
    private Bank bank = null;

    @Override
    public void connect(String[] args) {
        bank = new Bank();
        System.out.println("connected...");
    }

    @Override
    public void disconnect() {
        bank = null;
        System.out.println("disconnected...");
    }

    @Override
    public Bank getBank() {
        return bank;
    }

    static class Bank implements bank.Bank {

        private static int nextAccountNbr = 1000;
        private final Map<String, Account> accounts = new HashMap<>();

        @Override
        public Set<String> getAccountNumbers() {
            Set<String> ret = new HashSet<String>();
            for (Map.Entry<String, Account> entry : accounts.entrySet()) {
                if (entry.getValue().isActive())
                    ret.add(entry.getKey());
            }
            return ret;
        }

        @Override
        public String createAccount(String owner) {
            String accountNbr = Integer.toString(nextAccountNbr);
            nextAccountNbr++;
            Account account = new Account(owner, accountNbr);
            accounts.put(accountNbr, account);
            return accountNbr;
        }

        @Override
        public boolean closeAccount(String number) {
            Account account = accounts.get(number);
            if (!account.isActive() || account.getBalance() > 0)
                return false;
            account.active = false;
            return true;
        }

        @Override
        public bank.Account getAccount(String number) {
            return accounts.get(number);
        }

        @Override
        public void transfer(bank.Account from, bank.Account to, double amount)
                throws IOException, InactiveException, OverdrawException {
            if (amount <= 0)
                throw new RuntimeException(
                        "amount to transfer must be greater than 0");
            if (!from.isActive())
                throw new InactiveException();
            if (!to.isActive())
                throw new InactiveException();
            if (amount > from.getBalance())
                throw new OverdrawException();
            from.withdraw(amount);
            to.deposit(amount);
        }
    }

    static class Account implements bank.Account {
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
}