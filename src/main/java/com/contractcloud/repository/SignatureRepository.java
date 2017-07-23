package com.contractcloud.repository;

import com.contractcloud.domain.Signature;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Signature entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SignatureRepository extends MongoRepository<Signature,String> {

}
