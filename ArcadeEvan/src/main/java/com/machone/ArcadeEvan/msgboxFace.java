package com.machone.ArcadeEvan;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface msgboxFace extends CrudRepository<msgbox, Integer>{

	@Query(value="select * from msgbox union select * from msg1 where sender = :user or receiver = :user", nativeQuery=true)
	Iterable<msgbox> msgsByUsername(@Param("user") String user);

}
