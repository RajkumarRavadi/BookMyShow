package com.bookmyshow.BMSproject.Repositories;

import com.bookmyshow.BMSproject.Models.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowRepository extends JpaRepository<Show, Integer> {
}
