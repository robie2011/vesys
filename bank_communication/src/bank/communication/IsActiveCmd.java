package bank.communication;

import java.io.IOException;

import bank.Account;
import bank.Bank;

public class IsActiveCmd extends Request {

    private static final long serialVersionUID = 1L;
    private String accNbr;
    private boolean active;

    public IsActiveCmd(String accNbr) {
        this.accNbr = accNbr;
    }

    @Override
    public void handleRequest(Bank b) {
        try {
            Account acc = b.getAccount(accNbr);
            active = acc.isActive();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean getResponse() {
        return active;
    }

}
