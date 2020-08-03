package com.machone.ArcadeEvan;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


public interface resultsFace extends CrudRepository<results, Integer>{

	Iterable<results> findByGid(int gid);
	
	@Transactional
	long deleteByUid(int uid);
	
	
}
