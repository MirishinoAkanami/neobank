package controller;
import model.Account;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BankController {

    private List<Account> accounts;

    public BankController() {
        accounts = new ArrayList<>();
        accounts.add(new Account(1, "admin1234", "0000", true));
    }

    public Account login(int id, String password) {
        Account acc = getAccount(id);
        if (acc != null && acc.getPassword().equals(password)) return acc;
        if (acc != null && !acc.isAdmin()) acc.incrementFailed();
        return null;
    }

    public boolean register(int id, String password, String pin) {
        if (getAccount(id) != null) return false;
        if (password.length() < 8 || pin.length() != 4 || String.valueOf(id).length() > 6) return false;

        accounts.add(new Account(id, password, pin, false));
        return true;
    }

    public List<Account> getAllAccounts() {
        return accounts;
    }

    public Account getAccount(int id) {
        return accounts.stream().filter(acc -> acc.getId() == id).findFirst().orElse(null);
    }

    public void save(String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(accounts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            accounts = (List<Account>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous data found, starting fresh.");
        }
    }
    
    public boolean transfer(int fromId, int toId, double amt) {
        Account from = getAccount(fromId);
        Account to = getAccount(toId);

        if (from == null || to == null) return false;
        if (!from.withdraw(amt)) return false;

        to.deposit(amt);
        return true;
    }


}
