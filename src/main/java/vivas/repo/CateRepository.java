package vivas.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vivas.repo.entity.Cate;

@Repository
public interface CateRepository extends JpaRepository<Cate, Long> {
}
