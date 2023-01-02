package net.guides.springboot;


import com.mysql.cj.MysqlConnection;
import net.guides.springboot.entity.Student;
import net.guides.springboot.repository.StudentRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class SpringbootTestcontainersDemoApplicationTests extends AbstractContainerBaseTest{
	@Container
	private static MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:latest");

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private MockMvc mockMvc;

	// given/when/then format - BDD style
	@Test
	public void givenStudents_whenGetAllStudents_thenListOfStudents() throws Exception {
		// given - setup or precondition
		System.out.println(mySQLContainer.getDatabaseName());
		System.out.println(mySQLContainer.getPassword());
		System.out.println(mySQLContainer.getUsername());
		System.out.println(mySQLContainer.getJdbcUrl());
		List<Student> students =
				List.of(Student.builder().firstName("kaushlendra").lastName("tiwari").email("kaushlendra@gmail.com").build(),
						Student.builder().firstName("tony").lastName("stark").email("tony@gmail.com").build());
		studentRepository.saveAll(students);

		// when - action
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/students"));

		// then - verify the output
		response.andExpect(MockMvcResultMatchers.status().isOk());
		response.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(students.size())));
	}

}
