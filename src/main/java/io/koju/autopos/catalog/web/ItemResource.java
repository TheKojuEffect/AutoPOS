package io.koju.autopos.catalog.web;

import com.codahale.metrics.annotation.Timed;
import io.koju.autopos.catalog.domain.Item;
import io.koju.autopos.catalog.service.ItemCodeUtil;
import io.koju.autopos.catalog.service.ItemService;
import io.koju.autopos.catalog.struct.filter.ItemFilter;
import io.koju.autopos.web.rest.util.HeaderUtil;
import io.koju.autopos.web.rest.util.PaginationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Item.
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class ItemResource {

    private final ItemService itemService;

    @Autowired
    ItemResource(ItemService itemService) {
        this.itemService = itemService;
    }
    /**
     * POST  /items : Create a new item.
     *
     * @param item the item to create
     * @return the ResponseEntity with status 201 (Created) and with body the new item, or with status 400 (Bad Request) if the item has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/items",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Item> createItem(@Valid @RequestBody Item item) throws URISyntaxException {
        log.debug("REST request to save Item : {}", item);
        if (item.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("item", "idexists", "A new item cannot already have an ID")).body(null);
        }
        Item result = itemService.save(item);
        return ResponseEntity.created(new URI("/api/items/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("item", ItemCodeUtil.getCode(result.getId())))
                .body(result);
    }

    /**
     * PUT  /items : Updates an existing item.
     *
     * @param item the item to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated item,
     * or with status 400 (Bad Request) if the item is not valid,
     * or with status 500 (Internal Server Error) if the item couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/items",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Item> updateItem(@Valid @RequestBody Item item) throws URISyntaxException {
        log.debug("REST request to update Item : {}", item);
        if (item.getId() == null) {
            return createItem(item);
        }
        Item result = itemService.save(item);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("item", item.getId().toString()))
                .body(result);
    }

    /**
     * GET  /items : get all the items.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of items in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/items",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Item>> getAllItems(Pageable pageable, ItemFilter itemFilter)
        throws URISyntaxException {
        log.debug("REST request to get a page of Items");
        Page<Item> page = itemService.findAll(itemFilter, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/items");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /items/:id : get the "id" item.
     *
     * @param id the id of the item to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the item, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/items/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Item> getItem(@PathVariable Long id) {
        log.debug("REST request to get Item : {}", id);
        Item item = itemService.findOne(id);
        return Optional.ofNullable(item)
                .map(result -> new ResponseEntity<>(
                        result,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /items/:id : delete the "id" item.
     *
     * @param id the id of the item to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/items/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        log.debug("REST request to delete Item : {}", id);
        itemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("item", ItemCodeUtil.getCode(id))).build();
    }

}
