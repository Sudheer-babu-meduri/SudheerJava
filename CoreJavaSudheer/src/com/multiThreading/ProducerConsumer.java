package com.multiThreading;
 
  class Resource{
	  int i;
	  boolean status= false;
	  
	   synchronized void put(int i) throws InterruptedException {   // put() used to add the elements in synchronized way
		  if(status) {
			  wait();
		  }
		  this.i=i;
		  System.out.println("Put "+i);
		  status = true;
		   notify();
	  }
	  
	  synchronized void get() throws InterruptedException {      // get() used to remove the elements in synchronized way
		  if(!status) {
			  wait();
		  }
		  System.out.println("Get "+i);
		 status = false;
		 notify();
		  
	  }
  }
 

 class Producer implements Runnable{     //Producer which produces to the Resources
	 Resource r;
	 public Producer(Resource r) {
		 this.r = r;
		 
		 Thread t= new Thread(this,"Producer");
		 t.start();
	 
	 }
	 @Override
	 public void run() {
 		 int i=0;
		 while(true) {
 			 try {   
 				 r.put(i++);

				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		 }
	 }
	 
 }
 
 
 class Consumer implements Runnable{     // Consumer which consumes from the Resources
	 Resource r;
	 public Consumer(Resource r) {
		 this.r=r;
		Thread t= new Thread(this,"Consumer") ;// this means current Consumer Object 
		t.start();                             // and "Consumer" means name of Thread 
		 
		 
	 }
	 @Override
	 public void run() {
		  
		 while(true) {
			 try {
				 r.get();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		 }
	 }
 }

public class ProducerConsumer {

	public static void main(String[] args) {
		
		Resource r= new Resource();
		  new Producer(r);
		  new Consumer(r);	
		   
 	}

}

