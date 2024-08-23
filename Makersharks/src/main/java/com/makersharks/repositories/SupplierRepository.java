package com.makersharks.repositories;

import com.makersharks.entities.ManufacturingProcess;
import com.makersharks.entities.NatureOfBusiness;
import com.makersharks.entities.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Page<Supplier> findByLocationAndNatureOfBusinessAndManufacturingProcesses(String location,
                                                                              NatureOfBusiness natureOfBusiness,
                                                                              ManufacturingProcess manufacturingProcesses, Pageable pageable);
}
