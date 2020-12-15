package com.playtowin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.playtwowin.model.SubmittedFile;

@Repository
public interface SubmittedFileRepo extends CrudRepository<SubmittedFile, Integer>{

}
