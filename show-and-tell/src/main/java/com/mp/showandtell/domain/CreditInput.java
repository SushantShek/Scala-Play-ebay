package com.mp.showandtell.domain;


import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
@Data
public class CreditInput {

    private String name;
    private String address;
    private String postCode;
    private String phoneNumber;
    private String creditLimit;
    private String birthDate;
}
