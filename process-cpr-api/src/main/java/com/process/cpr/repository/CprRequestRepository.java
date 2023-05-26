package com.process.cpr.repository;

import com.process.cpr.service.CprRequest;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class CprRequestRepository {

    private final Map<Long, CprRequest> cprRequests = new HashMap<>();
    private long currentId = 1;

    public Long save(CprRequest cprRequest) {
        Long requestId = currentId++;
        cprRequests.put(requestId, cprRequest);
        return requestId;
    }

    public CprRequest findById(Long id) {
        return cprRequests.get(id);
    }
}
