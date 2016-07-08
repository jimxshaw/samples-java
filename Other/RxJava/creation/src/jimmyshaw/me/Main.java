package jimmyshaw.me;

import rx.Observable;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Observable<String> tweets = Observable.just("Hello, World!", "This is only a test.", "Where's the beef?");

        tweets.subscribe(tweet -> System.out.println(tweet));
        System.out.println();

        tweets.subscribe(System.out::println);
        System.out.println();
        
        List<Integer> primesList = Arrays.asList(2, 3, 5, 7, 11, 13, 17);
        Observable<Integer> primes = Observable.from(primesList);
        primes.subscribe(System.out::println);

        System.exit(0);
    }
}
