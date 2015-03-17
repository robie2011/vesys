package bank.communication;

import java.io.IOException;

import bank.Bank;

public class CreateCmd extends Request {

    private static final long serialVersionUID = 1L;
    private String owner;
    private String accNbr;

    public CreateCmd(String owner) {
        this.owner = owner;
    }

    @Override
    public void handleRequest(Bank b) {
        try {
            accNbr = b.createAccount(owner);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getResponse() {
        return accNbr;
    }

}
