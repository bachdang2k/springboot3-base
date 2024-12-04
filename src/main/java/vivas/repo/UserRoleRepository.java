package vivas.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vivas.repo.entity.UserRole;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Query(value = "select role_id from user_role where user_id = ?", nativeQuery = true)
    List<Integer> gRolesByUserId(Long userId);
}
