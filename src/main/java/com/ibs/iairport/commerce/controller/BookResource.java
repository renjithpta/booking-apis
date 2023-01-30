package com.ibs.iairport.commerce.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibs.iairport.commerce.dto.BookDto;
import com.ibs.iairport.commerce.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class BookResource {

    private final BookService bookService;

    
    public BookResource(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Gets the list of available books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the books",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class)) }),
    })
    @GetMapping(path = "/books", produces = {"application/json"})
    public ResponseEntity<List<BookDto>> list() {

        log.info("GET /api/v1/books");
        return ResponseEntity.ok(bookService.list());
    }

    @Operation(summary = "Get a book by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content) })
    @GetMapping(path = "/books/{id}", produces = {"application/json"})
    public ResponseEntity<BookDto> get(@PathVariable("id") Long bookId) {

        log.info("GET /api/v1/books/"+bookId);
        return ResponseEntity.ok(bookService.get(bookId));
    }

    @Operation(summary = "Creates a new book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created the book"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content) })
    @PostMapping(path = "/books", consumes = {"application/json"})
    public ResponseEntity<Void> create(@Valid @RequestBody BookDto bookDto) {

        log.info("POST /api/v1/books : "+bookDto);
        Long bookId = bookService.create(bookDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(bookId).toUri();

        return ResponseEntity.created(location).build();
    }
}
