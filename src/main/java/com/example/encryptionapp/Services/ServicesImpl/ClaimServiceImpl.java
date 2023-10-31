package com.example.encryptionapp.Services.ServicesImpl;

import com.example.encryptionapp.Entities.Claim;
import com.example.encryptionapp.Repositories.ClaimRepository;
import com.example.encryptionapp.Services.ClaimService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClaimServiceImpl implements ClaimService {
    private final ClaimRepository claimRepository;
    private final AESEncryptionService aesEncryptionService;

    @Override
    public Claim addClaim(Claim claim) {
        return claimRepository.save(aesEncryptionService.encryptClaim(claim));
    }

    @Override
    public List<Claim> getAllClaims() {
        List<Claim> claims = claimRepository.findAll();
        claims.forEach(aesEncryptionService::decryptClaim);
        return claims;
    }

    @Override
    public List<Claim> getClaimsByClientId(Long clientId) {
        List<Claim> claims = claimRepository.getClaimsByUser_Id(clientId);
        claims.forEach(aesEncryptionService::decryptClaim);
        return claims;    }
}
