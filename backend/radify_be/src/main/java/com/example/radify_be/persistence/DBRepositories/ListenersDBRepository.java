package com.example.radify_be.persistence.DBRepositories;

import com.example.radify_be.persistence.entities.ListenersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ListenersDBRepository extends JpaRepository<ListenersEntity, Integer> {


    @Query(value = "select count(*) FROM listeners where month(date) = :month and year(date) = :year and song_id=:id group by month(date)", nativeQuery = true)
    public Long getCount(@Param("month")String month, @Param("year")String year, @Param("id")Integer id);

    @Query(value = "select count(*) FROM listeners where year(date) = :year and song_id=:id group by year(date)", nativeQuery = true)
    public Long getYearlyCount(@Param("year")String year, @Param("id")Integer id);
}
