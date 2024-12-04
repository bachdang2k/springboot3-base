package vivas.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vivas.repo.entity.RefreshToken;
import vivas.repo.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUsername(String username);

    //@Modifying
    int deleteByUser(User user);

    List<RefreshToken> deleteByUsername(String username);

    List<RefreshToken> deleteByUserId(Integer userid);
}
