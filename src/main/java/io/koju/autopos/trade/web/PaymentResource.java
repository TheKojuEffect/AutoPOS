package io.koju.autopos.trade.web;

import com.codahale.metrics.annotation.Timed;
import io.koju.autopos.trade.domain.Payment;
import io.koju.autopos.trade.service.PaymentRepository;
import io.koju.autopos.web.rest.util.HeaderUtil;
import io.koju.autopos.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Payment.
 */
@RestController
@RequestMapping("/api")
public class PaymentResource {

    private final Logger log = LoggerFactory.getLogger(PaymentResource.class);
        
    @Inject
    private PaymentRepository paymentRepository;
    
    /**
     * POST  /payments : Create a new payment.
     *
     * @param payment the payment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new payment, or with status 400 (Bad Request) if the payment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/payments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Payment> createPayment(@Valid @RequestBody Payment payment) throws URISyntaxException {
        log.debug("REST request to save Payment : {}", payment);
        if (payment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("payment", "idexists", "A new payment cannot already have an ID")).body(null);
        }
        Payment result = paymentRepository.save(payment);
        return ResponseEntity.created(new URI("/api/payments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("payment", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payments : Updates an existing payment.
     *
     * @param payment the payment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated payment,
     * or with status 400 (Bad Request) if the payment is not valid,
     * or with status 500 (Internal Server Error) if the payment couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/payments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Payment> updatePayment(@Valid @RequestBody Payment payment) throws URISyntaxException {
        log.debug("REST request to update Payment : {}", payment);
        if (payment.getId() == null) {
            return createPayment(payment);
        }
        Payment result = paymentRepository.save(payment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("payment", payment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payments : get all the payments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of payments in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/payments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Payment>> getAllPayments(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Payments");
        Page<Payment> page = paymentRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/payments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /payments/:id : get the "id" payment.
     *
     * @param id the id of the payment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the payment, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/payments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Payment> getPayment(@PathVariable Long id) {
        log.debug("REST request to get Payment : {}", id);
        Payment payment = paymentRepository.findOne(id);
        return Optional.ofNullable(payment)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /payments/:id : delete the "id" payment.
     *
     * @param id the id of the payment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/payments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        log.debug("REST request to delete Payment : {}", id);
        paymentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("payment", id.toString())).build();
    }

}