package com.example.encryptionapp.Repositories;

import com.example.encryptionapp.Entities.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
    List<Claim> getClaimsByUser_Id(Long id);
}
