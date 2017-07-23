package com.contractcloud.repository;

import com.contractcloud.domain.Contract;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Contract entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContractRepository extends MongoRepository<Contract,String> {

}
