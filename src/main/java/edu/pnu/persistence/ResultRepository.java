package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {

}
