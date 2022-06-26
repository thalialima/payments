package br.com.ms.payments.controller;

import br.com.ms.payments.dto.PaymentDTO;
import br.com.ms.payments.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping(path = "/list")
    public Page<PaymentDTO> listPaymet(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageableDefault) {
        return paymentService.getAllPayments(pageableDefault);

    }

    @GetMapping(path = "/{id}")
    public PaymentDTO listById(@PathVariable @NotNull Long id) {
        return paymentService.getPaymentById(id);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody @NotNull PaymentDTO paymentDTO, UriComponentsBuilder uriBuilder) {
        PaymentDTO dto = paymentService.createPayment(paymentDTO);

        URI uri = uriBuilder.path("/payments/create/{id}").buildAndExpand(paymentDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<PaymentDTO> updatePayment(@RequestBody @NotNull PaymentDTO paymentDTO, @PathVariable @Valid Long id) {
        PaymentDTO dto = paymentService.updatePayment(paymentDTO, id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> removePayment(@PathVariable @NotNull Long id) {
        PaymentDTO paymentById = paymentService.getPaymentById(id);
        if (paymentById == null)
            return ResponseEntity.notFound().build();

        paymentService.deletePayment(id);
        return ResponseEntity.ok().build();
    }

}
