package com.bookmyshow.BMSproject.Repositories;

import com.bookmyshow.BMSproject.Models.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Integer> {


}
