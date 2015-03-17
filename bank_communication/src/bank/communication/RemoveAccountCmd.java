package bank.communication;

import java.io.IOException;

import bank.Bank;

public class RemoveAccountCmd extends Request {

    private static final long serialVersionUID = 1L;
    private String accNbr;
    private boolean closeAccount;

    public RemoveAccountCmd(String accNbr) {
        super();
        this.accNbr = accNbr;
    }

    @Override
    public void handleRequest(Bank b) {
        try {
            closeAccount = b.closeAccount(accNbr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean getResponse() {
        return closeAccount;
    }

}
