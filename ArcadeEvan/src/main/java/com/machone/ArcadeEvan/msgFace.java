package com.machone.ArcadeEvan;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface msgFace extends CrudRepository<msg, Integer>{
	
	@Transactional
	long deleteBySenduid(int senduid);
	
	@Transactional
	long deleteByReceiveuid(int receiveuid);
	
}
