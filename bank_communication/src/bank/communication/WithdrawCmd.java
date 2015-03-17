package bank.communication;

import java.io.IOException;

import bank.Account;
import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;

public class WithdrawCmd extends Request {

    private static final long serialVersionUID = 1L;
    private String accNbr;
    private double amount;

    public WithdrawCmd(String accNbr, double amount) {
        this.accNbr = accNbr;
        this.amount = amount;
    }

    @Override
    public void handleRequest(Bank b) {
        try {
            Account acc = b.getAccount(accNbr);
            acc.withdraw(amount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException | OverdrawException | InactiveException e) {
            setException(e);
        }
    }

    @Override
    public Object getResponse() {
        return null;
    }

}
