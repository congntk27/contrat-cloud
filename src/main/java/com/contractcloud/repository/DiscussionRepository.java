package com.contractcloud.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.contractcloud.domain.Discussion;

/**
 * Spring Data MongoDB repository for the Discussion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscussionRepository extends MongoRepository<Discussion, String>
{

	/**
	 * @param contractId
	 * @return
	 */
	Discussion findOneByContractId(String contractId);

}
