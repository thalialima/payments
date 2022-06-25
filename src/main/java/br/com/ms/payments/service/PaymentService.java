package br.com.ms.payments.service;

import br.com.ms.payments.dto.PaymentDTO;
import br.com.ms.payments.model.Payment;
import br.com.ms.payments.model.Status;
import br.com.ms.payments.repository.PaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<PaymentDTO> getAllPayments(Pageable pageable) {
        return paymentRepository
                .findAll(pageable)
                .map(payment -> modelMapper.map(payment, PaymentDTO.class));
    }

    public PaymentDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(payment, PaymentDTO.class);
    }

    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        Payment payment = modelMapper.map(paymentDTO, Payment.class);
        payment.setStatus(Status.CREATED);
        paymentRepository.save(payment);
        return modelMapper.map(payment, PaymentDTO.class);
    }

    public PaymentDTO updatePayment(PaymentDTO paymentDTO, Long id) {
        Payment payment = modelMapper.map(paymentDTO, Payment.class);
        payment.setId(id);
        paymentRepository.save(payment);
        return modelMapper.map(payment, PaymentDTO.class);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}
