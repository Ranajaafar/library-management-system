package maids.cc.librarymanagementsystem.patron.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maids.cc.librarymanagementsystem.patron.payload.request.PatronRequest;
import maids.cc.librarymanagementsystem.patron.payload.response.PatronDto;
import maids.cc.librarymanagementsystem.patron.service.PatronService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Configuration: Patron APIs", description = "Patron management endpoints")
@RequestMapping("/api/patrons")
@SecurityRequirement(name = "Bearer Authentication")
public class PatronController {
    private final PatronService patronService;

    @Operation(summary = "Get all patrons", description = " Retrieve a list of all patrons."  )
    @GetMapping()
    public ResponseEntity<List<PatronDto>> getAll(){
        log.info("Request to get all patrons");

        return ResponseEntity.ok(patronService.getAll());
    }


    @Operation(
            summary = "Get a Patron by ID",
            description = "Retrieve details of a specific Patron by ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<PatronDto> getProduct(@PathVariable("id") Long id){
        log.info("Request to get Patron by id: {}", id);

        return ResponseEntity.ok(patronService.findById(id));
    }


    //build create product REST API
    @Operation(
            summary = "Add a patron",
            description = "Add a new patron to the library."
    )
    @PostMapping()
    public ResponseEntity<PatronDto> issue(@Valid @RequestBody PatronRequest request) throws Exception {
        log.info("Request to create new Patron: {}", request);

        return new ResponseEntity<>(patronService.create(request), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Remove a patron",
            description = "Remove a patron from the library."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        log.info("Request to delete patron: {}", id);

        patronService.delete(id);
        return ResponseEntity.ok("patron deleted");

    }


    @Operation(
            summary = "Update a patron",
            description = "Update an existing patron's information."
    )
    @PutMapping("/{id}")
    public  ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody PatronRequest request){
        log.info("Request to update Patron: {}", id);

        return ResponseEntity.ok(patronService.update(request,id));
    }

}
