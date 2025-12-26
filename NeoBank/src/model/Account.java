package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable {

    private int id;
    private String password;
    private String pin;
    private double balance;
    private boolean admin;
    private boolean locked;
    private int failedAttempts;
    private List<String> history;

    public Account(int id, String password, String pin, boolean admin) {
        this.id = id;
        this.password = password;
        this.pin = pin;
        this.admin = admin;
        this.balance = 0;
        this.failedAttempts = 0;
        this.locked = false;
        this.history = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getPassword() { return password; }
    public boolean isAdmin() { return admin; }
    public boolean isLocked() { return locked; }
    public double getBalance() { return balance; }
    public List<String> getHistory() { return history; }
    public String getPin() { return pin; }

    public void deposit(double amt) {
        balance += amt;
        addHistory("Deposit " + amt);
    }

    public boolean withdraw(double amt) {
        if (amt <= 0 || balance < amt) return false;
        balance -= amt;
        addHistory("Withdraw " + amt);
        return true;
    }

    public boolean transfer(int targetId, double amt) {
        if (amt <= 0 || balance < amt) return false;
        Account target = view.BankGUI.controller.getAccount(targetId);
        if (target == null) return false;

        balance -= amt;
        target.balance += amt;

        addHistory("Transfer " + amt + " to " + targetId);
        target.addHistory("Received " + amt + " from " + id);

        return true;
    }

    public void setPin(String pin) { this.pin = pin; }
    public void unlock() { locked = false; failedAttempts = 0; }
    public void incrementFailed() {
        if (admin) return;
        failedAttempts++;
        if (failedAttempts >= 3) locked = true;
    }

    public void addHistory(String record) { history.add(record); }
}
