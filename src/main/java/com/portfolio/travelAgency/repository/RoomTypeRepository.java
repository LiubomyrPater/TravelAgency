package com.portfolio.travelAgency.repository;

import com.portfolio.travelAgency.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType,Integer> {

    Optional<RoomType> findByName(String name);

}
