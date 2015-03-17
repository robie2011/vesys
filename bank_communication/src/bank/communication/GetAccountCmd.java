package bank.communication;

import java.io.IOException;

import bank.Account;
import bank.Bank;

public class GetAccountCmd extends Request {

    private static final long serialVersionUID = 1L;
    
    /**
     * String array with account information:
     * 0 -> account number
     * 1 -> account owner
     */
    private String[] account = new String[2];

    public GetAccountCmd(String accNbr) {
        account[0] = accNbr;
    }

    @Override
    public void handleRequest(Bank b) {
        try {
            Account acc = b.getAccount(account[0]);
            if(acc != null) {
                account[0] = acc.getNumber();
                account[1] = acc.getOwner();    
            } else {
                account[0] = null;
                account[1] = null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String[] getResponse() {
        return account;
    }

}
