package bank.servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import bank.InactiveException;
import bank.OverdrawException;
import bank.communication.Request;

public abstract class AbstractServletProxy {
    
    protected Request sendRequest(Request req) {
        Request res = null;
        try {
            URL url = new URL("http://localhost:8080/bank_server/bank");
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("POST");
            c.setDoOutput(true);
            c.setDoInput(true);
            c.connect();

            ObjectOutputStream oos = new ObjectOutputStream(c.getOutputStream());
            oos.writeObject(req);
            oos.flush();

            ObjectInputStream ois = new ObjectInputStream(c.getInputStream());
            res = (Request) ois.readObject();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return res;
    }

    protected void handleException(Request resp) throws IOException,
            InactiveException, OverdrawException {
        if (resp.getException() != null) {
            try {
                Exception e = resp.getException();
                throw e;
            } catch (IOException | InactiveException | OverdrawException
                    | IllegalArgumentException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
