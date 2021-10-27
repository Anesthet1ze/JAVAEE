package com.info5059.casestudy.po;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.CrossOrigin;

//@CrossOrigin
@Repository

public interface PurchaseOrderRepository extends CrudRepository<PurchaseOrder, String> {
    @Modifying
    @Transactional
    PurchaseOrder getOne(String poid);

}
