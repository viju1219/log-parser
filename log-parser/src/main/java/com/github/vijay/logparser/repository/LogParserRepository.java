package com.github.vijay.logparser.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.github.vijay.logparser.modal.Alert;

@Repository
public interface LogParserRepository extends CrudRepository<Alert, String>{

}
