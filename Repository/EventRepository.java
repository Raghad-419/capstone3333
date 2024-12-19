package com.example.capston3.Repository;

import com.example.capston3.Model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Integer> {
    Event findEventById(Integer id);

    //Hashim Baroom
    List<Event> findAllByDate(LocalDate date);
}
