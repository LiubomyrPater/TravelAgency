package com.portfolio.travelAgency.repository;


import com.portfolio.travelAgency.entity.BookingsArchived;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchivedBookingRepository extends JpaRepository<BookingsArchived, Long> {

}
