package com.ibs.iairport.commerce.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibs.iairport.commerce.dto.AuthorDto;
import com.ibs.iairport.commerce.service.AuthorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class AuthorResource {

    private final AuthorService authorService;


    public AuthorResource(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Operation(summary = "Creates a new author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created the author"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content) })
    @PostMapping(path = "/authors", consumes = {"application/json"})
    public ResponseEntity<Void> create(@Valid @RequestBody AuthorDto authorData) {

        log.info("POST /api/v1/authors :"+authorData);
        Long adId = authorService.create(authorData);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(adId).toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Removes a requested author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content) })
    @DeleteMapping(path = "/authors/{id}", produces = {"application/json"})
    public ResponseEntity<Void> delete(@PathVariable("id") Long authorId) {

        log.info("DELETE /api/v1/authors/"+authorId);
        authorService.delete(authorId);
        return ResponseEntity.noContent().build();
    }
    

    @GetMapping(path = "/authors/{id}", produces = {"application/json"})
    public ResponseEntity<AuthorDto> get(@PathVariable("id") Long authorId) {

        log.info("GET /api/v1/authors/"+authorId);
        return ResponseEntity.ok(authorService.get(authorId));
    }
}
