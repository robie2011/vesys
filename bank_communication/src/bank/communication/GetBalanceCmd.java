package bank.communication;

import java.io.IOException;

import bank.Account;
import bank.Bank;

public class GetBalanceCmd extends Request {

    private static final long serialVersionUID = 1L;
    private String accNbr;
    private double balance;

    public GetBalanceCmd(String accNbr) {
        this.accNbr = accNbr;
    }

    @Override
    public void handleRequest(Bank b) {
        try {
            Account acc = b.getAccount(accNbr);
            balance = acc.getBalance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Double getResponse() {
        return balance;
    }

}
