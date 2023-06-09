package com.example.radify_be.persistence.DBRepositories;

import com.example.radify_be.persistence.entities.ListenersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListenersDBRepository extends JpaRepository<ListenersEntity, Integer> {


    @Query(value = "select count(*) FROM listeners where month(date) = :month and year(date) = :year and song_id=:id group by month(date)", nativeQuery = true)
    public Long getCount(@Param("month")String month, @Param("year")String year, @Param("id")Integer id);

    @Query(value = "select round(avg(count)) as avg from ( select count(*) as count, month(date) FROM listeners where song_id=:id group by month(date)) as t", nativeQuery = true)
    public Long getAvgCount(@Param("id")Integer id);

    @Query(value = "select song_id from listeners  where year(date) = :year  group by song_id order by count(*) desc limit 5 ", nativeQuery = true)
    public List<Long> getTopSongs(@Param("year")String year);


}
