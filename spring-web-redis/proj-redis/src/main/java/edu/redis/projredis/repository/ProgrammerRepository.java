package edu.redis.projredis.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProgrammerRepository {

	@Autowired
	private RedisTemplate<String, String> redisTempate;
	
	public void saveProgrammer(String key,String value)
	{
		redisTempate.opsForValue().set(key, value);
	}
	
	public String getProgrammer(String key)
	{
		return (String)redisTempate.opsForValue().get(key);
	}
	
	public List<String> getProgrammers(List<String> keys)
	{
		return redisTempate.opsForValue().multiGet(keys);
	}
	
	public void addToProgrammerListByLanguage(String language,String uid)
	{
		redisTempate.opsForList().rightPush(language, uid);
	}
	
	public List<String> getAllProgrammers(String language)
	{
		return redisTempate.opsForList().range(language, 0, -1);
	}
	
	public Long getProgrammerCount(String language)
	{
		return redisTempate.opsForList().size(language);
	}
	
	public void addToProgrammerSetByBloodType(String bloodType,String uid)
	{
		redisTempate.opsForSet().add(bloodType, uid);
	}
	
	public Set<String> getProgrammerSetByBloodType(String bloodType)
	{
		return redisTempate.opsForSet().members(bloodType);
	}
	
	public Boolean isProgrammerPresent(String bloodType,String uid)
	{
		return redisTempate.opsForSet().isMember(bloodType, uid);
	}
	
	public void addProgrammerInfo(Map<String,String> additionalInfo,String uid)
	{
		redisTempate.opsForHash().putAll("additional-"+uid, additionalInfo);
	}
	
	public void updateProgrammerInfo(String key,String value,String uid)
	{
		redisTempate.opsForHash().put("additional-"+uid, key, value);
	}
	
	public Map<Object,Object> getProgrammerInfo(String uid)
	{
		return redisTempate.opsForHash().entries("additional-"+uid);
	}
	
	public void deleteFromProgrammerInfo(String uid,Object[] keys)
	{
		redisTempate.opsForHash().delete("additional-"+uid, keys);
	}
	
	
}
