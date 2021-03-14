package edu.redis.projredis.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import edu.redis.projredis.model.Programmer;
import edu.redis.projredis.service.ProgrammerService;

@RestController
@RequestMapping("/api/programmer")
public class ProgrammerController {

	@Autowired
	private ProgrammerService programmerService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Programmer saveProgrammer(@RequestBody Programmer programmer) throws JsonProcessingException {
		programmer = programmerService.saveProgrammerDetails(programmer);

		return programmer;
	}

	@PostMapping("/{uid}/language/{language}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity addProgrammer(@PathVariable String uid, @PathVariable String language)
			throws JsonProcessingException {
		programmerService.addProgrammerToLanguage(language, uid);

		return ResponseEntity.status(HttpStatus.CREATED).body("Programmer added");
	}

	@PostMapping("/{uid}/blood-type/{bloodType}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity addProgrammerToBloodTypeSet(@PathVariable String uid, @PathVariable String bloodType)
			throws JsonProcessingException {
		Boolean response = programmerService.addProgrammerBloodType(bloodType, uid);

		if (response) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Programmer added");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body("Programmer already added to set");
		}
	}

	@GetMapping("/{uid}")
	public Programmer getProgrammer(@PathVariable String uid) throws JsonMappingException, JsonProcessingException {
		Programmer programmer = programmerService.getProgrammer(uid);
		return programmer;
	}

	@GetMapping("/language/{language}")
	public List<Programmer> getProgrammers(@PathVariable String language)
			throws JsonMappingException, JsonProcessingException {
		List<Programmer> programmers = programmerService.getProgrammersForLanguage(language);
		return programmers;
	}

	@GetMapping("/blood-type/{bloodType}")
	public List<Programmer> getProgrammersByBloodType(@PathVariable String bloodType)
			throws JsonMappingException, JsonProcessingException {
		List<Programmer> programmers = programmerService.getProgrammersByBloodType(bloodType);
		return programmers;
	}

	@PostMapping("/{uid}/additional-info")
	public ResponseEntity addProgrammerInfo(@PathVariable String uid, @RequestBody Map<String, String> info)
			throws JsonProcessingException {
		programmerService.addProgrammerInfo(info, uid);
		return ResponseEntity.status(HttpStatus.CREATED).body("Programmer Info added");
	}
	
	@PutMapping("/{uid}/additional-info/{key}")
	public ResponseEntity updateProgrammerInfo(@PathVariable String uid,@PathVariable String key,@RequestBody Map<String,String> value)
			throws JsonProcessingException {
		programmerService.updateProgrammerInfo(key, value.get("value"), uid);
		return ResponseEntity.status(HttpStatus.OK).body("Programmer info updated");
	}
	
	@DeleteMapping("/{uid}/additional-info")
	public ResponseEntity deleteProgrammerInfo(@PathVariable String uid,@RequestBody List<String> keys)
			throws JsonProcessingException {
		programmerService.deleteProgrammerInfo(uid, keys);
		return ResponseEntity.status(HttpStatus.OK).body("Programmer info deleted");
	}
	
	@GetMapping("/{uid}/additional-info")
	public Map<Object, Object> getProgrammerInfo(@PathVariable String uid)
			throws JsonProcessingException {
		return programmerService.getProgrammerInfo(uid);
		
	}

}
