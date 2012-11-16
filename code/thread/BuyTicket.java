package thread;

public class BuyTicket{
	
	public static void main(String[] args) {
		Ticket ticket = new Ticket(10);
		Buyer b1 = new Buyer(ticket,"张三", 1);
		Buyer b2 = new Buyer(ticket,"李斯", 4);
		Buyer b3 = new Buyer(ticket,"王五", 3);
		Buyer b4 = new Buyer(ticket,"赵六", 5);
		new Thread(b1).start();
		new Thread(b2).start();
		new Thread(b3).start();
		new Thread(b4).start();
	}
}

/**
 * 票源
 * @author xie041
 */
class Ticket{

	private int total;
	
	public Ticket(int total) {
		this.total = total;
	}

	public synchronized void sell(String buyer,int mount) {
		
		int current = total;
		
		if(total == 0){
			System.out.println(buyer+"购买，余票不足，总共剩余:0张");
			return;
		}
		if(mount > current){
			System.out.println(buyer+"购买，余票不足，要购买:"+mount+"张,总共剩余："+current + "张");
			return;
		}
		
		//票源足够
		current = current - mount;
		System.out.println(buyer+"购买:"+mount+"张成功,总共余票:"+current+"张");
		total = current;
	}
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}

class Buyer implements Runnable{

	private Ticket ticket;
	private int mount;
	private String name;
	
	@SuppressWarnings("unused")
	private Buyer(){}
	
	public Buyer(Ticket ticket,String name, int mount) {
		this.ticket = ticket;
		this.mount = mount;
		this.name = name;
	}

	@Override
	public synchronized void run() {
		ticket.sell(name,mount);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

