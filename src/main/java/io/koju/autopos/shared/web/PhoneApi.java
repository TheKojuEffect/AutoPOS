package io.koju.autopos.shared.web;

import com.codahale.metrics.annotation.Timed;
import io.koju.autopos.shared.domain.Phone;
import io.koju.autopos.shared.service.PhoneRepository;
import io.koju.autopos.web.rest.util.HeaderUtil;
import io.koju.autopos.web.rest.util.PaginationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
@RequestMapping(PhoneApi.API_PHONES)
@Slf4j
public class PhoneApi {

    static final String API_PHONES = "/api/phones";

    private final PhoneRepository phoneRepository;

    @Autowired
    public PhoneApi(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }


    @RequestMapping(method = POST, produces = APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Phone> createPhoneNumber(@Valid @RequestBody Phone phone) throws URISyntaxException {
        log.debug("REST request to save Phone : {}", phone);
        if (phone.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("phone", "idexists", "A new phone cannot already have an ID")).body(null);
        }
        Phone result = phoneRepository.save(phone);
        return ResponseEntity.created(new URI(API_PHONES + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("phone", result.getId().toString()))
                .body(result);
    }


    @RequestMapping(method = PUT, produces = APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Phone> updatePhoneNumber(@Valid @RequestBody Phone phone) throws URISyntaxException {
        log.debug("REST request to update Phone : {}", phone);
        if (phone.getId() == null) {
            return createPhoneNumber(phone);
        }
        Phone result = phoneRepository.save(phone);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("phone", phone.getId().toString()))
                .body(result);
    }


    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Phone>> getAllPhoneNumbers(Pageable pageable)
            throws URISyntaxException {
        log.debug("REST request to get a page of PhoneNumbers");
        Page<Phone> page = phoneRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, API_PHONES);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Phone> getPhoneNumber(@PathVariable Long id) {
        log.debug("REST request to get Phone : {}", id);
        Phone phone = phoneRepository.findOne(id);
        return Optional.ofNullable(phone)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @RequestMapping(value = "/{id}", method = DELETE, produces = APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePhoneNumber(@PathVariable Long id) {
        log.debug("REST request to delete Phone : {}", id);
        phoneRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("phone", id.toString())).build();
    }

}
