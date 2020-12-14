package com.playtowin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.playtwowin.model.Institution;

@Repository
public interface InstitutionRepo extends CrudRepository<Institution, Integer>{

}
