public class MultiThreadFibonacci extends Thread{
  public static void main(String[] args){
    Thread[] t = new Thread[5];
 
    long startTime = System.currentTimeMillis();
    for(int i=0; i<5; i++){
      t[i] = new MultiThreadFibonacci();
      t[i].start();
    }
 
    for(int i=0;i<5;i++){
      try{
        System.out.println("start thread " + i);
        t[i].join();
        System.out.println("started thread " + i);
      }catch(InterruptedException ie){}
    }
    long stopTime = System.currentTimeMillis();
    System.out.println("Duration=" + (stopTime-startTime));
  }
 
  @Override
  public void run(){
    System.out.println("Fib=" + fibonacci(45));
  }
 
  private long fibonacci(int n){
    if(n==0) return 0L;
    if(n==1) return 1L;
 
    return (fibonacci(n-1) + fibonacci(n-2));
  }
}