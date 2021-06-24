package ftmk.model;

/**
 * Represent teacher in hogwarts and subject associated to the teacher
 * @author emalianakasmuri
 *
 */
public class Teacher {
	
	
	private int id;
	private String name;
	private Subject subject;
	
	
	public Teacher () {  }
	
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
