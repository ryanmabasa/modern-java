package modernjavainaction.chap17.rxjava;

import io.reactivex.Observable;
import modernjavainaction.chap17.TempInfo;

import java.util.concurrent.TimeUnit;

/*
* In this statement, the onePerSec Observable emits one event per second. and on receipt of this message,
* the Subscriber fetches the temperature in New York and prints it.
* If you put this statement in a main method and try to execute it,
* however, you see nothing because the Observable publishing one event per second is executed in a thread
* that belongs to RxJava’s computation thread pool, which is made up of daemon threads.[[4]]
* But your main program terminates immediately and, in doing so, kills the daemon thread before it can produce any output.
* */

public class MainIntro {

    public static void main(String[] args) {
        Observable<String> strings = Observable.just( "first", "second" );

        Observable<Long> onePerSec = Observable.interval(1, TimeUnit.SECONDS);
        // RxJava's computation thread pool
      //  onePerSec.subscribe(i -> System.out.println(TempInfo.fetch( "New York" )));

        onePerSec.blockingSubscribe(
                i -> System.out.println(TempInfo.fetch( "New York" ))
        );

    }
}
