package controller;
import model.Account;

public class UserSession {
    private static Account current;

    public static void set(Account acc) {
        current = acc;
    }

    public static Account get() {
        return current;
    }

    public static void clear() {
        current = null;
    }

    public static boolean isAdmin() {
        return current != null && current.isAdmin();
    }

    public static int getId() {
        return current.getId();
    }

    public static double getBalance() {
        return current.getBalance();
    }

    public static String getPin() {
        return current.getPin();
    }

    public static void deposit(double amt) {
        current.deposit(amt);
    }

    public static boolean withdraw(double amt) {
        return current.withdraw(amt);
    }
}
