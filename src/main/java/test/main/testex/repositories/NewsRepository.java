package test.main.testex.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import test.main.testex.entity.News;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Integer> {

    @Query(value = "select * from news u", nativeQuery = true)
    List<News> findAllId();

}
