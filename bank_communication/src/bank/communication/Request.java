package bank.communication;

import java.io.Serializable;

import bank.Bank;

public abstract class Request implements Serializable {

    private static final long serialVersionUID = 1L;
    private Exception e;

    void setException(Exception e) {
        this.e = e;
    }

    public Exception getException() {
        return e;
    }

    public abstract void handleRequest(Bank b);
    
    public abstract Object getResponse();
}