package com.process.cpr.controller;

import com.process.cpr.dto.CprRequestDTO;
import com.process.cpr.service.CprRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cpr")
public class CprRequestController {

    private final CprRequestService cprRequestService;

    @Autowired
    public CprRequestController(CprRequestService cprRequestService) {
        this.cprRequestService = cprRequestService;
    }

    @PostMapping("/register")
    public ResponseEntity<Long> registerCprRequest(@RequestBody CprRequestDTO cprRequestDTO) {
        Long requestId = cprRequestService.registerCprRequest(cprRequestDTO);
        return ResponseEntity.ok(requestId);
    }

    @GetMapping("/request/{id}")
    public ResponseEntity<CprRequestDTO> getCprRequest(@PathVariable Long id) {
        CprRequestDTO cprRequestDTO = cprRequestService.getCprRequest(id);
        if (cprRequestDTO != null) {
            return ResponseEntity.ok(cprRequestDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/request/{id}")
    public ResponseEntity<Void> updateCprRequest(@PathVariable Long id, @RequestBody CprRequestDTO updatedCprRequestDTO) {
        boolean updated = cprRequestService.updateCprRequest(id, updatedCprRequestDTO);
        if (updated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
