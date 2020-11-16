package edu.redis.projredis.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Programmer {

	private UUID uid;
	private String name;
	private String companyName;

}
