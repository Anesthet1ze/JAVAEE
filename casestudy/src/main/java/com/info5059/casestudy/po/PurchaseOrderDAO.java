package com.info5059.casestudy.po;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import com.info5059.casestudy.product.Product;
import com.info5059.casestudy.product.ProductRepository;

//import com.info5059.casestudy.product.Product;
//import com.info5059.casestudy.product.ProductRepository;

import java.time.LocalDateTime;

@Component

public class PurchaseOrderDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProductRepository prodRepo;

    @Transactional
    public Long create(PurchaseOrder clientrep) {
        PurchaseOrder realPo = new PurchaseOrder();
        realPo.setPodate(LocalDateTime.now());
        realPo.setVendorid(clientrep.getVendorid());
        realPo.setAmount(clientrep.getAmount());
        entityManager.persist(realPo);
        for (PurchaseOrderLineitem item : clientrep.getItems()) {
            PurchaseOrderLineitem realItem = new PurchaseOrderLineitem();
            realItem.setPoid(realPo.getId());
            realItem.setProductid(item.getProductid());
            realItem.setPrice(item.getPrice());
            realItem.setQty(item.getQty());
            entityManager.persist(realItem);
            // we also need to update the QOO on the product table
            Product prod = prodRepo.getOne(item.getProductid());
            prod.setQoo(prod.getQoo() + item.getQty());
            prodRepo.saveAndFlush(prod);
        }
        return realPo.getId();
    }

    public PurchaseOrder findOne(Long id) {
        PurchaseOrder purchaseOrder = entityManager.find(PurchaseOrder.class, id);
        if (purchaseOrder == null) {
            throw new EntityNotFoundException("Can't find Purchase Order for ID " + id);
        }
        return purchaseOrder;
    }

}
