package com.playtowin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.playtwowin.model.Advisor;

@Repository
public interface AdvisorRepo extends CrudRepository<Advisor, Integer>{

}
