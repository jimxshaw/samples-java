package me.jimmyshaw;

/**
 * Created by Software Engineer on 7/1/2017.
 */
public class Money {
    private double value;

    private Currency currency;

    public Money(double value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public boolean isGreaterThan(Money operation) {
        return (normalized() > operation.normalized());
    }

    private double normalized() {
        return currency == Currency.USD ? value : value * currency.conversionRateTo(Currency.USD);
    }


}

class Test {
    private static void dispenseMoney(Money amount) {
        // implementation.....
    }

    public static void Test() {
        Money balance = new Money(3.6, Currency.USD);
        Money request = new Money(5.2, Currency.EURO);

        if (balance.isGreaterThan(request)) {
            dispenseMoney(request);
        }
    }
}

enum Currency {
    USD,
    EURO;

    public double conversionRateTo(Currency target) {
        return 1.3;
    }
}
