package linda.shm;

import linda.Callback;
import linda.Tuple;

public class Event {
	
	private Tuple template;
	private Callback callback;
	
	public Event(Tuple template, Callback callback) {
		super();
		this.template = template;
		this.callback = callback;
	}

	public Tuple getTemplate() {
		return template;
	}

	public void setTemplate(Tuple template) {
		this.template = template;
	}

	public Callback getCallback() {
		return callback;
	}

	public void setCallback(Callback callback) {
		this.callback = callback;
	}

}
