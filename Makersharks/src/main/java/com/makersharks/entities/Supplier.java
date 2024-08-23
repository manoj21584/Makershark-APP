package com.makersharks.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "suppliers")
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supplierId;
    private String companyName;
    private String website;
    private String location;

    @Enumerated(EnumType.STRING)
    private NatureOfBusiness natureOfBusiness;

    @Enumerated(EnumType.STRING)
    private ManufacturingProcess manufacturingProcesses;


}

