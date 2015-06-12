public class SingleThreadFibonacci {
  public static void main(String[] args){
    long startTime = System.currentTimeMillis();
    for(int i=0; i<5; i++){
      System.out.println("Fib=" + fibonacci(45));
    }
    long stopTime = System.currentTimeMillis();
    System.out.println("Duration=" + (stopTime - startTime));
  }
 
  private static long fibonacci(int n){
    if(n==0) return 0L;
    if(n==1) return 1L;
 
    return (fibonacci(n-1) + fibonacci(n-2));
  }
}