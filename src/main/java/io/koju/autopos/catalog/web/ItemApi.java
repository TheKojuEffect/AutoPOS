package io.koju.autopos.catalog.web;

import com.codahale.metrics.annotation.Timed;
import io.koju.autopos.catalog.domain.Item;
import io.koju.autopos.catalog.service.ItemCodeUtil;
import io.koju.autopos.catalog.service.ItemService;
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
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping(ItemApi.API_ITEMS)
@Slf4j
public class ItemApi {

    static final String API_ITEMS = "/api/items";

    private final ItemService itemService;

    @Autowired
    ItemApi(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping(method = POST, produces = APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Item> createItem(@Valid @RequestBody Item item) throws URISyntaxException {
        log.debug("REST request to save Item : {}", item);
        if (item.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("item", "idexists", "A new item cannot already have an ID")).body(null);
        }
        Item result = itemService.save(item);
        return ResponseEntity.created(new URI(API_ITEMS + result.getId()))
                             .headers(HeaderUtil.createEntityCreationAlert("item", result.getCode()))
                             .body(result);
    }

    @RequestMapping(method = PUT, produces = APPLICATION_JSON_VALUE)
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

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Item>> getAllItems(Pageable pageable,
                                                  @RequestParam(value = "q", required = false, defaultValue = "")
                                                          String query)
            throws URISyntaxException {
        log.debug("REST request to get a page of Items");
        Page<Item> page = itemService.findAll(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, API_ITEMS);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = GET, produces = APPLICATION_JSON_VALUE)
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

    @RequestMapping(value = "/{id}", method = DELETE, produces = APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        log.debug("REST request to delete Item : {}", id);
        itemService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("item", ItemCodeUtil.getCode(id))).build();
    }

}
