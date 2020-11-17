import java.util.ArrayList;
import java.util.Scanner;

public class Ricart implements Runnable {
	Thread t;
	static ArrayList<Process> order = new ArrayList<Process>();;

	public Ricart() {
		t = new Thread(this);
		t.start();
	}

	public void run() { // processes enter critical section
		Process p;
		while (true) {
			if (!(Ricart.order.isEmpty())) {
				p = Ricart.order.get(0);
				if (p.pen_queue.isEmpty()) {
					p.cs_flag = true;
					p.req_flag = false;
					System.out.println(p.name + " ENTERING INTO CRITICAL SECTION");
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(p.name + " EXITS FROM CRITICAL SECTION");
					p.cs_flag = false;
					for (Process h : p.req_queue) { // process giving reply message to pending requests
						h.Reply(p);
					}
					Ricart.order.remove(0);
				}
			} else {
				try {
					while (Ricart.order.isEmpty())
						Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

	}

	public static void main(String[] args) throws InterruptedException {
		Ricart r = new Ricart();
		int cnt = 1;
		Process p;
		System.out.println("ENTER NAME OF REQUESTING PROCESSES IN ORDER TO ENTER CRITICAL SECTION");
		while (true) {
			Scanner sc = new Scanner(System.in);
			String s = sc.nextLine();
			p = new Process(s);
			Ricart.order.add(p);
			for (Process t : Ricart.order) { // processes requesting for critical section execution
				if (!t.cs_flag) {
					for (Process v : Ricart.order) {
						if (!(t.equals(v))) {
							t.Request(v, cnt);
						}
					}
				}
				cnt++;
			}

		}

	}

}
