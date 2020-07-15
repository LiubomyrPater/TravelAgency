package com.portfolio.travelAgency.repository;

import com.portfolio.travelAgency.entity.Role;
import com.portfolio.travelAgency.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {


}
