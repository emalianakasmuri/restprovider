

package ftmk.rest.ws.client;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ftmk.model.Teacher;



/**
 * This code demonstrate the call made to web services host in 
 * http://localhost:8080/RESTProvider/ and process JSON response.
 * 
 * How to use this code?
 * Remove the comment that call the static method in the main method
 * 
 * 
 * @author emalianakasmuri
 *
 */
public class RESTClientApp {

	/**
	 * Main entry point of the application.
	 * 
	 * @param args
	 */
	public static void main (String args[]) {
		
		String baseURI = "http://localhost:8080/RESTProvider/services/teacher";
		String names[] = {"Flitwick", "Severus Snape", "albus dumbledore", "Albus Dumbledore"};
		
		try {
			
			/*for (String name:names)
				validateTeacher(baseURI, name); // success*/
						
			//getTeachersSubject(baseURI); // success
			
			//getTeachers(baseURI); // success
			
			//addTeacher(baseURI); // success
			
			//getFixedTeacher(baseURI); // success
			
			countTeachers(baseURI, "countteachers");
			
			// Attemp to search Flitwick
			//searchTechers(baseURI, "searchteachers", "wick");
			//searchTechers(baseURI, "searchteachers", "S");
			//searchTechers(baseURI, "searchteachers", "SNAPE");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		//getMessage(baseURI); // success
		
		
		System.out.println("\n\nEnd of excution for RESTClientApp");
	}
	
	
	/**
	 * This method find teachers based on the surname
	 * @param uri - base uri
	 * @param queryName - name of the service
	 * @param param - surname
	 * @throws IOException
	 */
	public static void searchTechers (String uri, String queryName, String param) throws IOException {
		
		// Create JAX-RS client
		Client client = ClientBuilder.newClient();

		// Get WebTarget for URL
		WebTarget webTarget = client.target(uri);
		webTarget = webTarget.path(queryName);
		System.out.println("Invoking " + webTarget.getUri());
		
		// Wrap in object
		Teacher paramTeacher = new Teacher();
		paramTeacher.setName(param);
		
		// Parse teacher to JSON format
		ObjectMapper mapper = new ObjectMapper();
		String jsonTeacher = mapper.writeValueAsString(paramTeacher);
		
		// Execute HTTP POST method
		Response response = webTarget.request().post
				(Entity.entity(jsonTeacher, MediaType.APPLICATION_JSON));

		// Check response code
		System.out.println("Response code: " + response.getStatus());
		if (response.getStatus() != 200) {
			// Display error message if response code is not 200

			System.out.println("Error invoking REST web service");
			System.out.println(response.getStatusInfo().getReasonPhrase());

			// Return to main
			return;
		}
		
		// Process search result
		String jsonResponse = response.readEntity(String.class);
		List<Teacher> result = mapper.readValue(jsonResponse, new TypeReference<List<Teacher>>() { });
		
		// Print header
		System.out.println("\nSearch Parameter (surname): " + param);
		System.out.println("Search Result");
		System.out.print("Total result: ");
		System.out.println(result.size());
		System.out.println("-----------------------------");
		
		// Print result
		int number = 0;
		for (Teacher teacher:result)
			System.out.println(++number + ". " + teacher.getName());
		
		System.out.println("\n\n");
		
	}
	
	/**
	 * This method demonstrate the call to GET web service and the steps to process
	 * JSON object from response.
	 * 
	 * @throws IOException
	 */
	public static void countTeachers (String uri, String queryName) throws IOException {
		
		
		// Create JAX-RS client
		Client client = ClientBuilder.newClient();

		// Get WebTarget for URL
		WebTarget webTarget = client.target(uri);
		webTarget = webTarget.path(queryName);
		System.out.println("Invoking " + webTarget.getUri());

		// Execute HTTP GET method
		Response response = webTarget.request().get();

		// Check response code
		if (response.getStatus() != 200) {
			// Display error message if response code is not 200

			System.out.println("Error invoking REST web service");
			System.out.println(response.getStatusInfo().getReasonPhrase());

			// Return to main
			return;
		}

		// REST call is a success.  Print the response.
		System.out.println("\nResponse target format " + response.getMediaType() + ":");
		int numberOfTeachers = response.readEntity(Integer.class);
		System.out.println("The number of teachers in Hogwarts: " + numberOfTeachers);
	}
	
	/**
	 * This method demonstrate the call to POST web service and the steps to process
	 * JSON object from response.
	 * 
	 * @throws IOException
	 */
	public static void validateTeacher(String uri, String queryName) throws IOException {
		
		// Create JAX-RS client
		Client client = ClientBuilder.newClient();

		// Get WebTarget for URL
		WebTarget webTarget = client.target(uri);
		webTarget = webTarget.path("validateteacher");

		// Create object 
		Teacher teacher = new Teacher();
		teacher.setName(queryName);
		
		// Parse teacher to JSON format
		ObjectMapper mapper = new ObjectMapper();
		String jsonTeacher = mapper.writeValueAsString(teacher);

		// Execute HTTP POST method
		Response response = webTarget.request().post(Entity.entity(jsonTeacher, MediaType.APPLICATION_JSON));
		
		// Check response code
		System.out.println("\nResponse code: " + response.getStatus());
		if (response.getStatus() != 200) {
			// Display error message if response code is not 200

			System.out.println("Error invoking REST web service");
			System.out.println(response.getStatusInfo().getReasonPhrase());

			// Return to main
			return;
		}
		
		// REST call is a success. Retrieve the output from Response.
		String jsonString = response.readEntity(String.class);
		System.out.println("In JSON format: " +  jsonString);
		
		System.out.println("Is " + queryName + " a valid teacher? " + jsonString);
		
	}
	
	/**
	 * This method print a list of teachers and their subject
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static void getTeachersSubject(String uri) throws JsonParseException, JsonMappingException, IOException {
		
		// Create JAX-RS client
		Client client = ClientBuilder.newClient();

		// Get WebTarget for URL
		WebTarget webTarget = client.target(uri);
		webTarget = webTarget.path("getteacherssubject");
		System.out.println(webTarget.getUri());
		
		// Execute HTTP GET method
		Response response = webTarget.request(MediaType.APPLICATION_JSON).get();

		// Check response code
		System.out.println("\nResponse code: " + response.getStatus());
		if (response.getStatus() != 200) {
			// Display error message if response code is not 200

			System.out.println("Error invoking REST web service");
			System.out.println(response.getStatusInfo().getReasonPhrase());

			// Return to main
			return;

		}
		
		// REST call is a success. Get the result
		String jsonResponse = response.readEntity(String.class);
		
		ObjectMapper mapper = new ObjectMapper();
		
		// Parse and print result using List
		List<Teacher> teachers = mapper.readValue(jsonResponse, new TypeReference<List<Teacher>>() { });
		System.out.println("\nList of Teachers in Hogwarts");
		for (Teacher teacher:teachers) {
			
			System.out.print("[" + teacher.getId() + "] " +teacher.getName());
			System.out.println( " : " + teacher.getSubject().getName());
			
		}
		
		
	}
	
	/**
	 * This method print a list of teachers
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static void getTeachers(String uri) 
			throws JsonParseException, JsonMappingException, IOException {
		
		// Create JAX-RS client
		Client client = ClientBuilder.newClient();

		// Get WebTarget for URL
		WebTarget webTarget = client.target(uri);
		webTarget = webTarget.path("getteachers");
		System.out.println(webTarget.getUri());
		
		// Execute HTTP GET method
		Response response = webTarget.request(MediaType.APPLICATION_JSON).get();

		// Check response code
		System.out.println("Response code: " + response.getStatus());
		if (response.getStatus() != 200) {
			// Display error message if response code is not 200

			System.out.println("Error invoking REST web service");
			System.out.println(response.getStatusInfo().getReasonPhrase());

			// Return to main
			return;

		}
		
		// REST call is a success. Get the result
		String jsonResponse = response.readEntity(String.class);
		
		ObjectMapper mapper = new ObjectMapper();
		
		// Method 1: Parse and print result using array
		// Source: https://mkyong.com/java/jackson-convert-json-array-string-to-list/
		Teacher teachersArray[] = mapper.readValue(jsonResponse, Teacher[].class);
		System.out.println("\nList of Teachers in Hogwarts (Method 1)");
		for (Teacher teacher:teachersArray)
			System.out.println(teacher.getId() + ":" +teacher.getName());
	
		// Method 2: Parse and print result using List
		List<Teacher> teacherList1 = Arrays.asList(mapper.readValue(jsonResponse, Teacher[].class));
		System.out.println("\nList of Teachers in Hogwarts (Method 2)");
		for (Teacher teacher:teacherList1)
			System.out.println(teacher.getId() + ":" +teacher.getName());
		
		// Method 3: Parse and print result using List
		List<Teacher> teacherList2 = mapper.readValue(jsonResponse, new TypeReference<List<Teacher>>() { });
		System.out.println("\nList of Teachers in Hogwarts (Method 3)");
		for (Teacher teacher:teacherList2)
			System.out.println(teacher.getId() + ":" +teacher.getName());
		
		
	}
	
	
	/**
	 * This method add teacher using JSON object
	 * @throws IOException
	 */
	public static void addTeacher(String uri) throws IOException {
		
		// Create JAX-RS client
		Client client = ClientBuilder.newClient();

		// Get WebTarget for URL
		WebTarget webTarget = client.target(uri);
		webTarget = webTarget.path("addteacher");

		// Create object 
		Teacher teacher = new Teacher();
		teacher.setName("Gilderoy Lockhart");
		
		// Parse teacher to JSON format
		ObjectMapper mapper = new ObjectMapper();
		String jsonTeacher = mapper.writeValueAsString(teacher);

		// Execute HTTP POST method
		Response response = webTarget.request().post
				(Entity.entity(jsonTeacher, MediaType.APPLICATION_JSON));
		
		// Check response code
		System.out.println("\n\nResponse code: " + response.getStatus());
		if (response.getStatus() != 200) {
			// Display error message if response code is not 200

			System.out.println("Error invoking REST web service");
			System.out.println(response.getStatusInfo().getReasonPhrase());

			// Return to main
			return;
		}
		
		// REST call is a success.  
		String jsonString = response.readEntity(String.class);
		
		// Parse to Teacher object
		teacher = mapper.readValue(jsonString, Teacher.class);

		System.out.println("\nResponse target format: " + response.getMediaType());
		System.out.print("Value from parsing:");
		System.out.println("("+ teacher.getId() +"): " + teacher.getName());
		
	}
	
	
	/**
	 * To display object of teacher
	 * The service return JSON
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static void getFixedTeacher (String uri) 
			throws JsonParseException, JsonMappingException, IOException {
		
		// Create JAX-RS client
		Client client = ClientBuilder.newClient();
		
		// Get WebTarget for URL
		WebTarget webTarget = client.target(uri);
		webTarget = webTarget.path("getteacher");
		System.out.println("\n\nInvoking: " + webTarget.getUri());
		
		// Execute HTTP GET method
		Response response = webTarget.request(MediaType.APPLICATION_JSON).get();
		
		
		// Check response code
		if (response.getStatus() != 200) {
			// Display error message if response code is not 200
			
			System.out.println("Error invoking REST web service");
			System.out.println(response.getStatusInfo().getReasonPhrase());
			
			// Return to main
			return;

		}
		
		// REST call is a success.  Extract response.
		String jsonString = response.readEntity(String.class);
		
		// Parser to Java object
		ObjectMapper mapper = new ObjectMapper();
		
		// Parse to Teacher object
		Teacher teacher = mapper.readValue(jsonString, Teacher.class);
		
		System.out.println("\nResponse target format: " + response.getMediaType());
		System.out.println("JSON format: " +  jsonString);
		System.out.println("Parsed to Java object: ("+ teacher.getId() +"): " + teacher.getName());
		
	}
	
	
	/**
	 * To display default value from TeacherService.
	 * The service return text/plain output.
	 */
	public static void getMessage (String uri) {
		
		
		// Create JAX-RS client
		Client client = ClientBuilder.newClient();
		
		// Specify the target URI
		WebTarget webTarget = client.target(uri);
		System.out.println("\n\nInvoking: " + webTarget.getUri());
		
		// Execute HTTP GET method
		Response response = webTarget.request().get();
		
		// Check response code
		if (response.getStatus() != 200) {
			// Display error message if response code is not 200
			
			System.out.println("Error invoking REST web service");
			System.out.println(response.getStatusInfo().getReasonPhrase());
			
			// Return to main
			return;
		}
		
		// REST call is a success.  Print the response.
		System.out.println("\nResponse target format " + response.getMediaType() + ":");
		System.out.println(response.readEntity(String.class));
		
	}
}
