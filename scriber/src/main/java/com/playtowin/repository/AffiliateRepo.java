package com.playtowin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.playtwowin.model.Affiliate;

@Repository
public interface AffiliateRepo extends CrudRepository<Affiliate, Integer>{

}
