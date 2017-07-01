package me.jimmyshaw;

import java.util.Currency;

interface Timesheet {
}

interface Invoice {
}

class Money {
    public Money(double val, Currency c) {
    }
}

class Worker {
    private String name;

    public Worker(String name) {
        this.name = name;
    }

    public void pay() {
        // implementation......
    }
}

class AccountsPayable {
    Worker[] workers;
    // implementation.........

    public void payEverybody() {
        for (Worker worker : workers) {
            worker.pay();
        }
    }
}