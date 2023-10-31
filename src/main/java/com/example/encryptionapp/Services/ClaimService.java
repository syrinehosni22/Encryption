package com.example.encryptionapp.Services;

import com.example.encryptionapp.Entities.Claim;

import java.util.List;

public interface ClaimService {
    Claim addClaim(Claim claim);

    List<Claim> getAllClaims();

    List<Claim> getClaimsByClientId(Long clientId);
}
