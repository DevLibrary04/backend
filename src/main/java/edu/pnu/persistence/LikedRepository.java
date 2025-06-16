package edu.pnu.persistence;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import edu.pnu.domain.Liked;

public interface LikedRepository extends JpaRepository<Liked, Long> {
	List<Liked> findByMember_UsernameOrderByTimestampDesc(String username);
}