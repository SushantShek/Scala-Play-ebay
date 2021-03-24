package com.mp.showandtell.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
@Data
public class CreditOutput {
    List<CreditInput> output;
}
