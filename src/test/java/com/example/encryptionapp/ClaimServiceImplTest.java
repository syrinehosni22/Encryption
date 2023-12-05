package com.example.encryptionapp;

import com.example.encryptionapp.Entities.Claim;
import com.example.encryptionapp.Repositories.ClaimRepository;
import com.example.encryptionapp.Services.ServicesImpl.AESEncryptionService;
import com.example.encryptionapp.Services.ServicesImpl.ClaimServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ClaimServiceImplTest {
    @Mock
    private ClaimRepository claimRepository;

    @Mock
    private AESEncryptionService aesEncryptionService;

    @InjectMocks
    private ClaimServiceImpl claimService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddClaim() {
        Claim claim = new Claim();
        when(aesEncryptionService.encryptClaim(any(Claim.class))).thenReturn(claim);
        when(claimRepository.save(claim)).thenReturn(claim);

        Claim result = claimService.addClaim(claim);

        verify(aesEncryptionService, times(1)).encryptClaim(any(Claim.class));
        verify(claimRepository, times(1)).save(claim);
        assertEquals(claim, result);
    }

    @Test
    public void testGetAllClaims() {
        List<Claim> claims = new ArrayList<>();
        claims.add(new Claim());
        when(claimRepository.findAll()).thenReturn(claims);
        when(aesEncryptionService.decryptClaim(any(Claim.class))).thenReturn(new Claim());

        List<Claim> result = claimService.getAllClaims();

        verify(claimRepository, times(1)).findAll();
        verify(aesEncryptionService, times(claims.size())).decryptClaim(any(Claim.class));
        assertEquals(claims.size(), result.size());
    }

    @Test
    public void testGetClaimsByClientId() {
        Long clientId = 1L;
        List<Claim> claims = new ArrayList<>();
        claims.add(new Claim());
        when(claimRepository.getClaimsByUser_Id(clientId)).thenReturn(claims);
        when(aesEncryptionService.decryptClaim(any(Claim.class))).thenReturn(new Claim());

        List<Claim> result = claimService.getClaimsByClientId(clientId);

        verify(claimRepository, times(1)).getClaimsByUser_Id(clientId);
        verify(aesEncryptionService, times(claims.size())).decryptClaim(any(Claim.class));
        assertEquals(claims.size(), result.size());
    }
}
