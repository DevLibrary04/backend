package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.History;

public interface HistoryRepository extends JpaRepository<History, Long> {
	List<History> findByMemberUsername(String username);
}
