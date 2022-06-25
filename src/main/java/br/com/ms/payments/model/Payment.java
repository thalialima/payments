package br.com.ms.payments.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Entity
@Table(name = "payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Positive
    private BigDecimal value;
    @NotBlank
    @Size(max = 100)
    private String name;
    @NotBlank
    @Size(max = 19)
    private String number;
    @NotBlank
    @Size(min = 7)
    private String expiration;
    @NotBlank
    @Size(min = 3, max = 3)
    private String code;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;
    @NotNull
    private Long orderId;
    @NotNull
    private Long paymentMode;
}
