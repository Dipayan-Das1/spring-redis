package edu.redis.projredis.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.redis.projredis.model.Programmer;
import edu.redis.projredis.repository.ProgrammerRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProgrammerService {

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProgrammerRepository programmerRepository;
	
	public Programmer saveProgrammerDetails(Programmer programmer) throws JsonProcessingException
	{
		if(programmer.getUid()==null)
		{
			programmer.setUid(UUID.randomUUID());
		}
		String value = objectMapper.writeValueAsString(programmer);
		log.info(value);
		programmerRepository.saveProgrammer(programmer.getUid().toString(), value);
		return programmer;	
	}
	
	public void addProgrammerToLanguage(String language,String uid)
	{
		programmerRepository.addToProgrammerListByLanguage(language, uid);
	}
	
	public List<Programmer> getProgrammersForLanguage(String language)
	{
		 List<Programmer> programmers = null;
		List<String> prgrmmrs = programmerRepository.getAllProgrammers(language);
		if(prgrmmrs!=null)
		{
			List<String> prgrmrVals = programmerRepository.getProgrammers(prgrmmrs);
			if(prgrmrVals!=null)
			{
				programmers = prgrmrVals.stream().map(val -> {
					Programmer programmer;
					try {
						programmer = objectMapper.readValue(val, Programmer.class);
						return programmer;
					} catch (JsonProcessingException e) {
						log.error(e.getMessage());
						throw new RuntimeException(e);
					}
					
				}).collect(Collectors.toList());
			}
			
		}
		return programmers;
	}
	
	public Programmer getProgrammer(String uuid) throws JsonMappingException, JsonProcessingException
	{
		String value = programmerRepository.getProgrammer(uuid);
		if(value == null)
		{
			throw new IllegalArgumentException("Invalid programmer id");
		}
		
		Programmer programmer = objectMapper.readValue(value, Programmer.class);
		return programmer;		
	}
	
	public Boolean addProgrammerBloodType(String bloodType,String uid)
	{
		if(programmerRepository.isProgrammerPresent(bloodType, uid))
		{
			return false;
		}
		
		programmerRepository.addToProgrammerSetByBloodType(bloodType, uid);
		return true;
	}
	
	public List<Programmer> getProgrammersByBloodType(String bloodType)
	{
		 List<Programmer> programmers = null;
		Set<String> prgrmmrs = programmerRepository.getProgrammerSetByBloodType(bloodType);
		if(prgrmmrs!=null)
		{
			List<String> prgrmrVals = programmerRepository.getProgrammers(prgrmmrs.stream().collect(Collectors.toList()));
			if(prgrmrVals!=null)
			{
				programmers = prgrmrVals.stream().map(val -> {
					Programmer programmer;
					try {
						programmer = objectMapper.readValue(val, Programmer.class);
						return programmer;
					} catch (JsonProcessingException e) {
						log.error(e.getMessage());
						throw new RuntimeException(e);
					}
					
				}).collect(Collectors.toList());
			}
			
		}
		return programmers;
	}
	
	
	
	public void addProgrammerInfo(Map<String,String> info,String uid)
	{
		programmerRepository.addProgrammerInfo(info, uid);
	}
	
	public void deleteProgrammerInfo(String uid,List<String> keys)
	{
		programmerRepository.deleteFromProgrammerInfo(uid, keys.toArray());
	}
	
	public void updateProgrammerInfo(String key,String value,String uid)
	{
		programmerRepository.updateProgrammerInfo(key, value, uid);
	}
	
	public Map<Object,Object> getProgrammerInfo(String uid)
	{
		return programmerRepository.getProgrammerInfo(uid);
	}
}
