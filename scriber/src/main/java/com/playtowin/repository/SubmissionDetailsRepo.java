package com.playtowin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.playtwowin.model.SubmissionDetails;

@Repository
public interface SubmissionDetailsRepo extends CrudRepository<SubmissionDetails, Integer>{

}
