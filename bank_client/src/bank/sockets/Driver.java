//package bank.sockets;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.Serializable;
//import java.net.Socket;
//import java.util.Set;
//
//import bank.Account;
//import bank.BankDriver;
//import bank.InactiveException;
//import bank.OverdrawException;
//import bank.communication.CreateCommand;
//import bank.communication.DepositCommand;
//import bank.communication.GetAccountCommand;
//import bank.communication.GetAccountsCommand;
//import bank.communication.GetBalanceCommand;
//import bank.communication.GetOwnerCommand;
//import bank.communication.IsActiveCommand;
//import bank.communication.RemoveCommand;
//import bank.communication.TransferCommand;
//import bank.communication.WithdrawCommand;
//
//public class Driver implements BankDriver {
//    private Bank bank = null;
//    private Socket socket;
//    private ObjectOutputStream oos;
//    private ObjectInputStream ois;
//
//    @Override
//    public void connect(String[] args) throws IOException {
//        String host = "localhost";
//        int port = 1234;
//        if (args.length > 0) {
//            host = args[0];
//        }
//        if (args.length > 1) {
//            port = Integer.parseInt(args[1]);
//        }
//        System.out.println("connecting to " + host + ":" + port);
//        socket = new Socket(host, port, null, 0);
//        oos = new ObjectOutputStream(socket.getOutputStream());
//        ois = new ObjectInputStream(socket.getInputStream());
//        bank = new Bank(oos, ois);
//    }
//
//    @Override
//    public void disconnect() throws IOException {
//        oos.close();
//        ois.close();
//        socket.close();
//    }
//
//    @Override
//    public Bank getBank() {
//        return bank;
//    }
//
//    static class Bank implements bank.Bank, Serializable {
//
//        private static final long serialVersionUID = 1L;
//        private ObjectOutputStream oos;
//        private ObjectInputStream ois;
//
//        public Bank(ObjectOutputStream oos, ObjectInputStream ois) {
//            this.oos = oos;
//            this.ois = ois;
//        }
//
//        @Override
//        public Set<String> getAccountNumbers() throws IOException {
//            Set<String> accNbrs = null;
//            GetAccountsCommand getAccCmd = new GetAccountsCommand();
//            oos.writeObject(getAccCmd);
//
//            try {
//                accNbrs = (Set<String>) ois.readObject();
//            } catch (ClassNotFoundException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            return accNbrs;
//        }
//
//        @Override
//        public String createAccount(String owner) throws IOException {
//            String accNbr = null;
//            CreateCommand create = new CreateCommand();
//            create.owner = owner;
//            oos.writeObject(create);
//
//            try {
//                accNbr = (String) ois.readObject();
//            } catch (ClassNotFoundException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            return accNbr;
//        }
//
//        @Override
//        public boolean closeAccount(String number) throws IOException {
//            boolean closeAcc = false;
//            RemoveCommand remove = new RemoveCommand();
//            remove.number = number;
//            oos.writeObject(remove);
//
//            try {
//                closeAcc = (boolean) ois.readObject();
//            } catch (ClassNotFoundException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            return closeAcc;
//        }
//
//        @Override
//        public AccountWrapper getAccount(String number) throws IOException {
//            AccountWrapper acc = null;
//            GetAccountCommand get = new GetAccountCommand();
//            get.number = number;
//            oos.writeObject(get);
//
//            try {
//                Account accInner = (Account) ois.readObject();
//                acc = new AccountWrapper(accInner, oos, ois);
//            } catch (ClassNotFoundException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            return acc;
//        }
//
//        @Override
//        public void transfer(bank.Account from, bank.Account to, double amount)
//                throws IOException, InactiveException, OverdrawException {
//            TransferCommand transfer = new TransferCommand();
//            transfer.numberFrom = from.getNumber();
//            transfer.numberTo = to.getNumber();
//            transfer.amount = amount;
//
//            oos.writeObject(transfer);
//
//            try {
//                Object ret = ois.readObject();
//                if (ret instanceof InactiveException) {
//                    throw (InactiveException) ret;
//                } else if (ret instanceof OverdrawException) {
//                    throw (OverdrawException) ret;
//                }
//            } catch (ClassNotFoundException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//    }
//
//    static class AccountWrapper implements bank.Account, Serializable {
//        private static final long serialVersionUID = 1L;
//        private Account inner;
//        private ObjectOutputStream oos;
//        private ObjectInputStream ois;
//
//        AccountWrapper(Account inner, ObjectOutputStream oos,
//                ObjectInputStream ois) {
//            this.inner = inner;
//            this.oos = oos;
//            this.ois = ois;
//        }
//
//        @Override
//        public double getBalance() throws IOException {
//            double ret = 0.0;
//            GetBalanceCommand balance = new GetBalanceCommand();
//            balance.number = inner.getNumber();
//            oos.writeObject(balance);
//
//            try {
//                ret = (Double) ois.readObject();
//            } catch (ClassNotFoundException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            return ret;
//        }
//
//        @Override
//        public String getOwner() throws IOException {
//            String owner = "";
//            GetOwnerCommand cmd = new GetOwnerCommand();
//            cmd.number = inner.getNumber();
//            oos.writeObject(cmd);
//
//            try {
//                owner = (String) ois.readObject();
//            } catch (ClassNotFoundException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            return owner;
//        }
//
//        @Override
//        public String getNumber() throws IOException {
//            return inner.getNumber();
//        }
//
//        @Override
//        public boolean isActive() throws IOException {
//            boolean active = false;
//            IsActiveCommand cmd = new IsActiveCommand();
//            cmd.number = inner.getNumber();
//            oos.writeObject(cmd);
//
//            try {
//                active = (boolean) ois.readObject();
//            } catch (ClassNotFoundException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            return active;
//        }
//
//        @Override
//        public void deposit(double amount) throws InactiveException,
//                IOException {
//            DepositCommand deposit = new DepositCommand();
//            deposit.number = inner.getNumber();
//            deposit.amount = amount;
//            oos.writeObject(deposit);
//            try {
//                Object ret = ois.readObject();
//                if (ret instanceof InactiveException) {
//                    throw (InactiveException) ret;
//                }
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        public void withdraw(double amount) throws InactiveException,
//                OverdrawException, IOException {
//            WithdrawCommand cmd = new WithdrawCommand();
//            cmd.accountNbr = inner.getNumber();
//            cmd.amount = amount;
//            oos.writeObject(cmd);
//
//            try {
//                Object ret = ois.readObject();
//                if (ret instanceof InactiveException) {
//                    throw (InactiveException) ret;
//                } else if (ret instanceof OverdrawException) {
//                    throw (OverdrawException) ret;
//                }
//            } catch (ClassNotFoundException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//    }
//}
