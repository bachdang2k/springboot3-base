package vivas.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vivas.repo.entity.User;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long>  {
    Boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
