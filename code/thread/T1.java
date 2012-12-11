package thread;

public class T1 implements Runnable{

/**
* 票总数
*/
private int count = 5;
/**
 * 售票点
*/
private String name;
public T1(String name) {
	super();
	this.name = name;
}

@Override
public void run() {
	for (int i = 0; i < count; i++) {
		sale();
	}
}

public synchronized void sale() {
	if (count > 0) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("售票点"+name+"卖出票："+count--);
	}
}

public static void main(String[] args) {
	T1 t = new T1("A");
	Thread t1 = new Thread(t);
	Thread t2 = new Thread(t);
	Thread t3 = new Thread(t);
	t1.start();
	t2.start();
	t3.start();
}
}
