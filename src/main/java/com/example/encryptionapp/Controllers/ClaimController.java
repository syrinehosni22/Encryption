package com.example.encryptionapp.Controllers;

import com.example.encryptionapp.Entities.Claim;
import com.example.encryptionapp.Services.ClaimService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claim")
@AllArgsConstructor
public class ClaimController {
    private final ClaimService claimService;

    @PostMapping("/add")
    public Claim addClaim(@RequestBody Claim claim) {
        return claimService.addClaim(claim);
    }

    @GetMapping("/all")
    public List<Claim> getAllClaims() {
        return claimService.getAllClaims();
    }

    @GetMapping("/user/{id}")
    public List<Claim> getClaimsByUserId(@PathVariable("id") Long id){
        return claimService.getClaimsByClientId(id);
    }
}
