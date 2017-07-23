package com.contractcloud.repository;

import com.contractcloud.domain.Discussion;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Discussion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscussionRepository extends MongoRepository<Discussion,String> {

}
