package hogwarts.rest.model;


/**
 * This class represent the teachers/professors in Hogwarts
 * 
 * @author emalianakasmuri
 *
 */

public class Teacher {
	
	
	private int id;
	private String name;
	
	/* The subject taught by the teacher */
	private Subject subject;
	
	
	public Teacher() { } 
	
	public Teacher(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	

}
