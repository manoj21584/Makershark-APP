package com.makersharks.dto;

import com.makersharks.entities.ManufacturingProcess;
import com.makersharks.entities.NatureOfBusiness;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class SupplierCreateDTO {
    private Long supplierId;
    @NotBlank(message = "Company name cannot be blank or contain only whitespace")
    @Pattern(regexp = "^[\\S]+$", message = "Company name must not contain any whitespace")
    private String companyName;

    @NotBlank(message = "Website cannot be blank or contain only whitespace")
    @Pattern(regexp = "^(https?:\\/\\/)?([\\w\\d\\-_]+\\.)+[\\w\\d\\-_]+(\\/.*)?$", message = "Invalid website URL format")
    private String website;

    @NotBlank(message = "Location cannot be blank and cant contain any whitespace")
    @Pattern(regexp = "^[\\S]+$", message = "location name must not contain any whitespace")
    private String location;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Nature of business cannot be blank")
    @Pattern(regexp = "^[\\S]+$", message = "natureOfBusiness cant be blank and  can not contain any whitespace")
    private String natureOfBusiness;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Nature of business cannot be blank")
    @Pattern(regexp = "^[\\S]+$", message = "manufacturingProcesses cant be blank and  can not contain any whitespace")
    private String manufacturingProcesses;


}
