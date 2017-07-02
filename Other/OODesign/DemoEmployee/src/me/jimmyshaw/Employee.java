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
        // implementation...
    }
}

class Employee extends Worker {
    private Timesheet[] unpaidTimesheets;

    public Employee(String name) {
        super(name);
    }

    public void attachTimesheet(Timesheet i) {

    }
}

class Contractor extends Worker {
    private Invoice[] invoicesDue;

    public Contractor(String name) {
        super(name);
    }

    public void attachInvoice(Invoice i) {
        
    }
}

class AccountsPayable {
    Worker[] workers;
    // implementation...

    public void payEverybody() {
        for (Worker worker : workers) {
            worker.pay();
        }
    }

}



