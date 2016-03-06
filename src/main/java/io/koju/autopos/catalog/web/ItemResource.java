package io.koju.autopos.catalog.web;

import com.codahale.metrics.annotation.Timed;
import io.koju.autopos.catalog.domain.Item;
import io.koju.autopos.catalog.service.ItemCodeUtil;
import io.koju.autopos.catalog.service.ItemService;
import io.koju.autopos.web.rest.util.HeaderUtil;
import io.koju.autopos.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * REST controller for managing Item.
 */
@RestController
@RequestMapping(value = "/api", produces = APPLICATION_JSON_VALUE)
public class ItemResource {

    private final Logger log = LoggerFactory.getLogger(ItemResource.class);

    @Inject
    private ItemService itemService;

    /**
     * POST  /items -> Create a new item.
     */
    @RequestMapping(value = "/items",
        method = POST)
    @Timed
    public ResponseEntity<Item> createItem(@Valid @RequestBody Item item) throws URISyntaxException {
        log.debug("REST request to save Item : {}", item);
        if (item.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("item", "idexists", "A new item cannot already have an ID")).body(null);
        }
        Item result = itemService.save(item);
        return ResponseEntity.created(new URI("/api/items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("item", ItemCodeUtil.getCode(result.getId()).toString()))
            .body(result);
    }

    /**
     * PUT  /items -> Updates an existing item.
     */
    @RequestMapping(value = "/items",
        method = PUT)
    @Timed
    public ResponseEntity<Item> updateItem(@Valid @RequestBody Item item) throws URISyntaxException {
        log.debug("REST request to update Item : {}", item);
        if (item.getId() == null) {
            return createItem(item);
        }
        Item result = itemService.save(item);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("item", item.getCode().toString()))
            .body(result);
    }

    /**
     * GET  /items -> get all the items.
     */
    @RequestMapping(value = "/items",
        method = GET)
    @Timed
    public ResponseEntity<List<Item>> getAllItems(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Items");
        Page<Item> page = itemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/items");
        return new ResponseEntity<>(page.getContent(), headers, OK);
    }

    /**
     * GET  /items/:id -> get the "id" item.
     */
    @RequestMapping(value = "/items/{id}",
        method = GET)
    @Timed
    public ResponseEntity<Item> getItem(@PathVariable Long id) {
        log.debug("REST request to get Item : {}", id);
        Item item = itemService.findOne(id);
        return Optional.ofNullable(item)
            .map(result -> new ResponseEntity<>(
                result,
                OK))
            .orElse(new ResponseEntity<>(NOT_FOUND));
    }

    /**
     * DELETE  /items/:id -> delete the "id" item.
     */
    @RequestMapping(value = "/items/{id}",
        method = DELETE)
    @Timed
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        log.debug("REST request to delete Item : {}", id);
        itemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("item", ItemCodeUtil.getCode(id).toString())).build();
    }
}
