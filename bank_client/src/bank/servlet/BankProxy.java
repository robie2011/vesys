package bank.servlet;

import java.io.IOException;
import java.util.Set;

import bank.Account;
import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;
import bank.communication.CreateCmd;
import bank.communication.GetAccountCmd;
import bank.communication.GetAccountsCmd;
import bank.communication.RemoveAccountCmd;
import bank.communication.TransferCmd;

public class BankProxy extends AbstractServletProxy implements Bank {

    private Account account;

    @Override
    public String createAccount(String owner) throws IOException {
        CreateCmd createCmd = new CreateCmd(owner);
        createCmd = (CreateCmd) sendRequest(createCmd);
        return createCmd.getResponse();
    }

    @Override
    public boolean closeAccount(String number) throws IOException {
        RemoveAccountCmd removeAccountCmd = new RemoveAccountCmd(number);
        removeAccountCmd = (RemoveAccountCmd) sendRequest(removeAccountCmd);
        return removeAccountCmd.getResponse();
    }

    @Override
    public Set<String> getAccountNumbers() throws IOException {
        GetAccountsCmd getAccountsCmd = new GetAccountsCmd();
        getAccountsCmd = (GetAccountsCmd) sendRequest(getAccountsCmd);
        return getAccountsCmd.getResponse();
    }

    @Override
    public Account getAccount(String number) throws IOException {
        GetAccountCmd getAccountCmd = new GetAccountCmd(number);
        getAccountCmd = (GetAccountCmd) sendRequest(getAccountCmd);
        if (getAccountCmd.getResponse()[0] != null) {
            account = new AccountProxy(getAccountCmd.getResponse()[0], getAccountCmd.getResponse()[1]);
        } else {
            account = null;
        }
        return account;
    }

    @Override
    public void transfer(Account a, Account b, double amount)
            throws IOException, IllegalArgumentException, OverdrawException,
            InactiveException {
        TransferCmd transferCmd = new TransferCmd(a.getNumber(), b.getNumber(),
                amount);
        transferCmd = (TransferCmd) sendRequest(transferCmd);
        handleException(transferCmd);
    }
}
