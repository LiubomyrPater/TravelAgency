package com.portfolio.travelAgency.repository;

import com.portfolio.travelAgency.entity.Hotel;
import com.portfolio.travelAgency.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType,Integer> {

    Optional<RoomType> findByNameAndAndHotel(String name, Hotel hotel);

    List<RoomType> findAllByHotel(Hotel hotel);

}
