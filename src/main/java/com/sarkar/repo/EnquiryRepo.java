package com.sarkar.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sarkar.entity.Enquiry;

@Repository
public interface EnquiryRepo extends JpaRepository<Enquiry, Integer> {


    // Use 'counsellor.id' to access the counsellor's 'id' field
    @Query("SELECT e FROM Enquiry e WHERE e.counsellor.id = :counsellorId")
    List<Enquiry> getEnquiryByCounsellorId(@Param("counsellorId") Integer counsellorId);
    
}
