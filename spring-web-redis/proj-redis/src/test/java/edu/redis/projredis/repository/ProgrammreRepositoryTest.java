package edu.redis.projredis.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import edu.redis.projredis.config.TestRedisConfiguration;
import edu.redis.projredis.model.Programmer;
import edu.redis.projredis.service.ProgrammerService;

@ActiveProfiles("test")
@SpringBootTest(classes = TestRedisConfiguration.class)
public class ProgrammreRepositoryTest {
	@Autowired
    private ProgrammerService programmerService;
	
	Programmer prg = null; 
	
	@BeforeEach
	public  void setup() throws JsonProcessingException
	{
		prg = new Programmer();
		prg.setCompanyName("test-company");
		prg.setName("dummy-name");
		prg = programmerService.saveProgrammerDetails(prg);
	}
	
	@Test
	public void testGetProgrammer() throws JsonMappingException, JsonProcessingException {
		
		prg = programmerService.getProgrammer(prg.getUid().toString());
		assertEquals("test-company", prg.getCompanyName());
		assertEquals("dummy-name", prg.getName());
	}
	
	@Test
	public void testListFuntions() throws JsonMappingException, JsonProcessingException {
		
		programmerService.addProgrammerToLanguage("Java", prg.getUid().toString());
		List<Programmer> prgs = programmerService.getProgrammersForLanguage("Java");
		assertEquals(1,prgs.size());
	}
	
	
	


}
