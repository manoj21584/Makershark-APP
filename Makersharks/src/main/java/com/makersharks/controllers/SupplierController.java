package com.makersharks.controllers;

import com.makersharks.dto.SupplierCreateDTO;
import com.makersharks.dto.SupplierRequestDTO;
import com.makersharks.entities.Supplier;
import com.makersharks.services.SupplierService;
import com.makersharks.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping("/create")
    public ResponseEntity<Supplier> createSupplier(@Valid @RequestBody SupplierCreateDTO supplierCreateDTO) {
        Supplier supplier = supplierService.createSupplier(supplierCreateDTO);
        return ResponseEntity.ok(supplier);
    }

    @PostMapping("/query")
    public ResponseEntity<List<SupplierCreateDTO>> getSuppliersByCriteria(
            @Valid @RequestBody SupplierRequestDTO supplierRequestDTO,
            @RequestParam(value = "pageNo",defaultValue =AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value="pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = AppConstants.DEFAULT_SOR_BY,required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false) String sortDir) {
        List<SupplierCreateDTO> suppliers = supplierService.getSuppliersByCriteria(
                supplierRequestDTO.getLocation(),
                supplierRequestDTO.getNatureOfBusiness().toUpperCase(),
                supplierRequestDTO.getManufacturingProcess().toUpperCase(),
                pageNo,
                pageSize,
                sortBy,
                sortDir

        );
        return ResponseEntity.ok(suppliers);
    }
    @PutMapping("/update/{supplierId}")
    public ResponseEntity<SupplierCreateDTO>updateSupplierById(@RequestBody SupplierCreateDTO supplierCreateDTO,@PathVariable Long supplierId){
        SupplierCreateDTO updatedSupplier = supplierService.updateSupplier(supplierCreateDTO,supplierId);
        return ResponseEntity.ok(updatedSupplier);
    }
    @DeleteMapping("/delete/{suppierId}")
    public ResponseEntity<String> deleteSupplier(@PathVariable Long suppierId){
        String s = supplierService.deleteSupplier(suppierId);
        return ResponseEntity.ok(s);
    }
    @GetMapping
    public ResponseEntity<List<SupplierCreateDTO>> getAllSupplier(){
        List<SupplierCreateDTO> allSuppliers=supplierService.getAllSuppliers();
        return ResponseEntity.ok(allSuppliers);
    }

}
