package hogwarts.rest.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import hogwarts.rest.manager.TeacherDataManager;
import hogwarts.rest.model.Teacher;

import javax.ws.rs.core.GenericEntity;


/**
 * This class represent the service provides through the web.
 * 
 * This class tells the framework when the URI is -> http://localhost:8080/RESTProvider/services/teacher
 * it is referring to all the services declared in this class.
 * 
 * @author emalianakasmuri
 *
 */


@Path("/teacher")
public class TeacherService {
	
	/**
	 * This method find teachers based on the surname
	 * @param teacher - techer to be find
	 * @return
	 */
	@POST
	@Path("/searchteachers/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchTeachers(Teacher teacher) {
		

		// Get teachers and subjects
		TeacherDataManager dataMgr = new TeacherDataManager();
		List<Teacher> teachers = dataMgr.getHogwartTeachers(teacher.getName());
		
		// Parse to GenericEntity
		GenericEntity<List<Teacher>> genericEntity = new GenericEntity<List<Teacher>>(teachers) {};
		
		// Return through Response
		return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
		
	}
	
	/**
	 * This method count the number of teachers in Hogwarts
	 * @return
	 */
	@GET
	@Path("/countteachers")
	@Produces(MediaType.TEXT_PLAIN)
	public Integer countTeachers() {
		
		TeacherDataManager dataMgr = new TeacherDataManager();
		
		List<Teacher> teachers = dataMgr.getHogwartTeachers();
		
		int numberOfTeacher = teachers.size();
		
		return numberOfTeacher;
	}
	
	
	
	/**
	 * This service validates the existance of the teacher
	 * @param teacher: The teacher to be validated
	 * @return true/false in JSON
	 * 	true: Valid teacher. The teacher is not exist in Hogwarts.
	 * 	false: invalid teacher.  The teacher is not exist in Hogwarts.
	 */
	@POST
	@Path("/validateteacher")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean validateTeacher (Teacher teacher) {
		
		TeacherDataManager dataMgr = new TeacherDataManager();
		
		boolean flag = dataMgr.isValidTeacher(teacher.getName());
		
		return flag;
		
	}
	

	/**
	 * This service retrieve a list of teachers with the subject assigned to them.
	 * @return A list of teacher with assigned subject in JSON
	 */
	@GET
	@Path("/getteacherssubject")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTeachersSubject() {
		
		// Get teachers and subjects
		TeacherDataManager dataMgr = new TeacherDataManager();
		List<Teacher> teachers = dataMgr.getHogwartsTeacherSubject();
		
		// Parse to GenericEntity
		GenericEntity<List<Teacher>> genericEntity = new GenericEntity<List<Teacher>>(teachers) {};
		
		// Return through Response
		return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
		
		
	}
	
	/**
	 * This service retrieve a list of teachers 
	 * @return
	 */
	@GET
	@Path("/getteachers")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTeachers() {
		
		
		TeacherDataManager dataMgr = new TeacherDataManager();
		List<Teacher> teachers = dataMgr.getHogwartTeachers();
		
		// Parse to GenericEntity
		GenericEntity<List<Teacher>> genericEntity = new GenericEntity<List<Teacher>>(teachers) {};
		
		
		return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
		
	}
	
	
	/**
	 * This demonstrate POST for a service.
	 * The object is not stored to the list maintained in TeacherDataManager.
	 * @return
	 */
	@POST
	@Path("/addteacher")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Teacher addTeacher(Teacher teacher) {
		
		teacher.setId(4);
		
		return teacher;
		
	}
	
	/**
	 * This service retrieve a teacher based on the teacher id
	 * @param id: Teacher id
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getteacher/{teacherid}")
	public Teacher getTeacher (@PathParam("teacherid") int id) {
		
		TeacherDataManager dataMgr = new TeacherDataManager();
		Teacher teacher = dataMgr.getTeacher(id);
		
		return teacher;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getteacher")
	public Teacher getFixedTeacher () {
		
		Teacher teacher = new Teacher(1, "Minerva McGonagall");
		
		return teacher;
	}
	
	
	/**
	 * Default GET message.
	 * @return
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getDefaultTeacherMessage () {
		
		String message = "Alahamora! Brilliant connection to "
				+ "http://localhost:8080/RESTProvider/services/teacher";
		
		System.out.println(message);
		
		return message;
	}

}
