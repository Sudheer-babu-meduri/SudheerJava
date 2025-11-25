//producer adds the element and multiple consumers consumes the element one after another
package com.multiThreading;

//Shared Resource
  class Resource1 {
    int i;
    boolean status = false;  // indicates product is ready
    int turn = 1;            // 1 = Consumer-1, 2 = Consumer-2 , 3 = Consumer-3

    synchronized void put(int i) throws InterruptedException {
        while (status) {
            wait();
        }
        this.i = i;
        System.out.println("Put: " + i);
        status = true;
        notifyAll();           // calling Threads which are in waiting state
    }
    

    synchronized void get(int consumerId) throws InterruptedException {
        while (!status || turn != consumerId) {
            wait();
        }
        System.out.println("Consumer-" + consumerId + " get: " + i);

        status = false;

        // switch turn among 1, 2 and 3
        if (turn == 1) {
        	 turn = 2;
        }else if(turn==2) {
        	turn=3;
        }
        else if(turn==3){
        	turn = 1;
        }

        notifyAll();   // calling Threads which are in waiting state
    }
}


//Producer Thread
 class Producer1 implements Runnable {
	Resource1 r;

	public Producer1(Resource1 r) {    
		this.r = r;
		Thread t = new Thread(this, "Producer");   // passing current Thread Object and name of Thread
		t.start();
	}

	@Override
	public void run() {
		int i = 0;
		while (true) {
			try {
				r.put(i++);
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

//Consumer Thread
class Consumer1 implements Runnable {
    Resource1 r;
    int id;

    public Consumer1(Resource1 r, int id) {         
        this.r = r;
        this.id = id;
        Thread t=new Thread(this, "Consumer");           // passing current Thread Object and name of Thread
        t.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                r.get(id);
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
//Main Class
public class MultipleConsumers {
	public static void main(String[] args) {
		Resource1 r = new Resource1();

		 new Producer1(r);

		 new Consumer1(r, 1);
	        new Consumer1(r, 2);
	        new Consumer1(r,3);
	}
}


