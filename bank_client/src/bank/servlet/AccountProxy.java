package bank.servlet;

import java.io.IOException;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;
import bank.communication.DepositCmd;
import bank.communication.GetBalanceCmd;
import bank.communication.IsActiveCmd;
import bank.communication.WithdrawCmd;

public class AccountProxy extends AbstractServletProxy implements Account {

    private String accNbr;
    private String owner;

    public AccountProxy(String accNbr, String owner) {
        this.accNbr = accNbr;
        this.owner = owner;
    }

    @Override
    public String getNumber() throws IOException {
        return accNbr;
    }

    @Override
    public String getOwner() throws IOException {
        return owner;
    }

    @Override
    public boolean isActive() throws IOException {
        IsActiveCmd isActiveCmd = new IsActiveCmd(accNbr);
        isActiveCmd = (IsActiveCmd) sendRequest(isActiveCmd);
        return isActiveCmd.getResponse();
    }

    @Override
    public void deposit(double amount) throws IOException,
            IllegalArgumentException, InactiveException {
        DepositCmd depositCmd = new DepositCmd(accNbr, amount);
        depositCmd = (DepositCmd) sendRequest(depositCmd);
        try {
            handleException(depositCmd);
        } catch (OverdrawException e) {
        }
    }

    @Override
    public void withdraw(double amount) throws IOException,
            IllegalArgumentException, OverdrawException, InactiveException {
        WithdrawCmd withdrawCmd = new WithdrawCmd(accNbr, amount);
        withdrawCmd = (WithdrawCmd) sendRequest(withdrawCmd);
        handleException(withdrawCmd);
    }

    @Override
    public double getBalance() throws IOException {
        GetBalanceCmd getBalanceCmd = new GetBalanceCmd(accNbr);
        getBalanceCmd = (GetBalanceCmd) sendRequest(getBalanceCmd);
        return getBalanceCmd.getResponse();
    }

}
