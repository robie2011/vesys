package bank.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import bank.InactiveException;
import bank.OverdrawException;

public class Bank implements bank.Bank {

    private static int nextAccountNbr = 1000;
    private final Map<String, Account> accounts = new HashMap<>();

    @Override
    public Set<String> getAccountNumbers() throws IOException {
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
        // account.active = false;
        account.setActive(false);
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