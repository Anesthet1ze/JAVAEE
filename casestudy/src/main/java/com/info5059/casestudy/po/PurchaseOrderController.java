package com.info5059.casestudy.po;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController

public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderDAO poDAO;
    @Autowired
    private PurchaseOrderRepository purchaseorderRepository;

    @GetMapping("/api/purchaseorders")
    public ResponseEntity<Iterable<PurchaseOrder>> findAll() {
        Iterable<PurchaseOrder> purchaseorders = purchaseorderRepository.findAll();
        return new ResponseEntity<Iterable<PurchaseOrder>>(purchaseorders, HttpStatus.OK);
    }

    @PostMapping("/api/purchaseorders")
    public ResponseEntity<Long> addOne(@RequestBody PurchaseOrder clientrep) { // use RequestBody here
        Long poId = poDAO.create(clientrep);
        return new ResponseEntity<Long>(poId, HttpStatus.OK);

    }
}
