package com.multiThreading;

import java.util.ArrayList;
import java.util.List;

class Resource3{
	    int i;
	   List<Integer> li=new ArrayList<>();  // Shared List 
	     boolean status= false;
	  
	   synchronized void put(int i) throws InterruptedException {  // put() used to add the elements in synchronized way
		  if(status) {
			  wait();
		  }
		  this.i=i;
		  li.add(i);
		  System.out.println("Put "+i);
		  status = true;
		   notify();
	  }
	  
	  synchronized void get() throws InterruptedException {    //get() used to add the elements in synchronized way
		  if(!status) {
			  wait();
		  }
		  li.remove(li.indexOf(i));
		  System.out.println("Get "+i);
		 status = false;
		 notify();
		  System.out.println(li);
	  }
}
//Producer which produces to the Resources
 
class Producer3 implements Runnable{     // producer class which calls put() method to add elements to List
	 Resource3 r;
	 public Producer3(Resource3 r) {
		 this.r = r;
		 
		 Thread t= new Thread(this,"Producer3");
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
//Consumer which consumes from the Resources

class Consumer3 implements Runnable{          // Consumer class which calls get() method to remove elements from List
	 Resource3 r;
	 public Consumer3(Resource3 r) {
		 this.r=r;
		Thread t= new Thread(this,"Consumer3") ;// this means current Consumer Object 
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

public class CollectionSharedBuffer {

	public static void main(String[] args) {
		
		Resource3 r= new Resource3();
		  new Producer3(r);
		  new Consumer3(r);	
		   
	}

}

