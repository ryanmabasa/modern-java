package modernjavainaction.chap16;

import static modernjavainaction.chap16.Util.delay;
import static modernjavainaction.chap16.Util.format;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class AsyncShop {

  private final String name;
  private final Random random;

  public AsyncShop(String name) {
    this.name = name;
    random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
  }

  /*
  * 16.2 Implementing an Asynchronous API
  * Listing 16.4: Implementing the getPriceAsync method
  * Converting a Synchronous Method into an Asynchronous One
  *
  * 16.2.2 Dealing with Errors
The code you’ve developed so far works correctly if everything goes smoothly.
* But what happens if the price calculation generates an error? Unfortunately,
* in this case you get a particularly negative outcome: the exception raised to signal
* the error remains confined in the thread, which is trying to calculate the product price,
* and ultimately kills the thread. As a consequence, the client remains blocked forever,
* waiting for the result of the get method to arrive.

The client can prevent this problem by using an overloaded version of the get method that also accepts a timeout.
* It’s good practice to use a timeout to prevent similar situations elsewhere in your code. This way,
* the client at least avoids waiting indefinitely, but when the timeout expires,
* it’s notified with a TimeoutException. As a consequence,
* the client won’t have a chance to discover what caused that failure inside the thread
* that was trying to calculate the product price. To make the client aware of the reason
* why the shop wasn’t able to provide the price of the requested product,
* you have to propagate the Exception that caused the problem inside the
* CompletableFuture through its completeExceptionally method.
  * */

  public Future<Double> getPrice(String product) {
/*
    CompletableFuture<Double> futurePrice = new CompletableFuture<>();
    new Thread(() -> {
      try {
        double price = calculatePrice(product);
        futurePrice.complete(price);
      } catch (Exception ex) {
        futurePrice.completeExceptionally(ex);
      }
    }).start();
    return futurePrice;
*/
    return CompletableFuture.supplyAsync(() -> calculatePrice(product));
  }

  /*
  *
  * The supplyAsync method accepts a Supplier as argument and returns a
  * CompletableFuture that will be asynchronously completed with the value obtained by invoking that Supplier.
  * This Supplier is run by one of the Executors in the ForkJoinPool,
  *  but you can specify a different Executor by passing it as a second argument to the overloaded version of this method. More generally, you can pass an Executor to all other CompletableFuture factory methods. You use this capability in section 16.3.4, where we demonstrate that using an Executor
  * that fits the characteristics of your application can have a positive effect on its performance.
  * Also note that the CompletableFuture returned by the getPriceAsync method in listing 16.7 is
  * equivalent to the one you created and completed manually in listing 16.6,
  * meaning that it provides the same error management you carefully added.
  *
  * */

  private double calculatePrice(String product) {
    delay();
    if (true) {
      throw new RuntimeException("product not available");
    }
    return format(random.nextDouble() * product.charAt(0) + product.charAt(1));
  }

}
