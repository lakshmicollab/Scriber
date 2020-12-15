package com.playtowin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.playtwowin.model.DigitalSignature;

@Repository
public interface DigitalSignatureRepo extends CrudRepository<DigitalSignature, Integer>{

}
