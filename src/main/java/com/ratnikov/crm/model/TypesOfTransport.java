package com.ratnikov.crm.model;

import com.ratnikov.crm.enums.TransportCode;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "TYPES_OF_TRANSPORT")
public class TypesOfTransport {

    @Id
    @Enumerated(EnumType.STRING)
    private TransportCode code;
    private String fullName;
    private Double minLength;
    private Double maxLength;
    private Double minWeight;
    private Double maxWeight;
    private Integer transportCapacity;

}
