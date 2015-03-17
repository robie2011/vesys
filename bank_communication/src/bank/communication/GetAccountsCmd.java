package bank.communication;

import java.io.IOException;
import java.util.Set;

import bank.Bank;

public class GetAccountsCmd extends Request {

    private static final long serialVersionUID = 1L;
    private Set<String> accountNumbers;

    @Override
    public void handleRequest(Bank b) {
        try {
            accountNumbers = b.getAccountNumbers();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<String> getResponse() {
        return accountNumbers;
    }

}
