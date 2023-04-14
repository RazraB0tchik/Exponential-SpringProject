package test.main.testex.repositories;

import test.main.testex.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshRepository extends JpaRepository<RefreshToken, Integer> {
    RefreshToken findRefreshTokenById(int id);
}
