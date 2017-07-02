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

interface Payable {
    void pay();
}

abstract class Worker implements Payable {
    private String name;

    public Worker(String name) {
        this.name = name;
    }

    public void pay() {
        Money due = getAmountDue();
    }

    abstract protected Money getAmountDue();
}

class Employee extends Worker {
    private Timesheet[] unpaidTimesheets;

    public Employee(String name) {
        super(name);
    }

    public void attachTimesheet(Timesheet i) {

    }

    protected Money getAmountDue() {
        // implementation...
        return new Money(99.99, Currency.getInstance("USD"));
    }
}

class Contractor extends Worker {
    private Invoice[] invoicesDue;

    public Contractor(String name) {
        super(name);
    }

    public void attachInvoice(Invoice i) {

    }

    protected Money getAmountDue() {
        return new Money(129.99, Currency.getInstance("USD"));
    }
}

class AccountsPayable {
    private Payable[] creditors;
    // implementation...

    public void payEverybody() {
        for (Payable creditor : creditors) {
            creditor.pay();
        }
    }

}



