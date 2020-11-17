import java.util.ArrayList;

public class Process {
	String name;
	ArrayList<Process> req_queue;
	ArrayList<Process> pen_queue;
	int req_tstamp;
	boolean cs_flag, req_flag;

	public Process(String name) {
		this.name = name;
		req_queue = new ArrayList<Process>();
		pen_queue = new ArrayList<Process>();
		req_tstamp = 0;
		cs_flag = false;
		req_flag = false;
	}

	public void Request(Process p, int i) { // Process Requesting cs Execution to another Process
		if (this.cs_flag != true) {
			this.req_tstamp = i;
			req_flag = true;
			if (p.cs_flag == true || (p.req_flag == true && p.req_tstamp >= this.req_tstamp)) {
				p.req_queue.add(this);
				this.pen_queue.add(p);
			} else
				p.Reply(this);
		}
	}

	public void Reply(Process p) { // Process p removed from pending queue
		this.pen_queue.remove(p);
	}

}
