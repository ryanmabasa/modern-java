package modernjavainaction.chap17.rxjava;

import io.reactivex.Observable;
import modernjavainaction.chap17.TempInfo;

import static modernjavainaction.chap17.rxjava.TempObservable.getTemperature;

public class MainIntro2 {
    public static void main(String[] args) {
        Observable<TempInfo> observable = getTemperature( "New York" );
        observable.blockingSubscribe( new TempObserver() );


    }
}
