package bank.communication;

import java.io.IOException;

import bank.Account;
import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;

public class TransferCmd extends Request {

    private static final long serialVersionUID = 1L;
    private String accNbrFrom;
    private String accNbrTo;
    private double amount;

    public TransferCmd(String accNbrFrom, String accNbrTo, double amount) {
        this.accNbrFrom = accNbrFrom;
        this.accNbrTo = accNbrTo;
        this.amount = amount;
    }

    @Override
    public void handleRequest(Bank b) {
        try {
            Account accFrom = b.getAccount(accNbrFrom);
            Account accTo = b.getAccount(accNbrTo);
            b.transfer(accFrom, accTo, amount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException | OverdrawException
                | InactiveException e) {
            setException(e);
        }
    }

    @Override
    public Object getResponse() {
        return null;
    }

}
