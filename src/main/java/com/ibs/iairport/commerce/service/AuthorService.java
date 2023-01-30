package com.ibs.iairport.commerce.service;

import com.ibs.iairport.commerce.dto.AuthorDto;
import com.ibs.iairport.commerce.exception.DuplicatedEntityException;
import com.ibs.iairport.commerce.exception.EntityNotFoundException;
import com.ibs.iairport.commerce.model.Author;
import com.ibs.iairport.commerce.repository.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;


    public AuthorService(AuthorRepository authorRepository, ModelMapper modelMapper) {
        this.authorRepository = authorRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Long create(AuthorDto authorDto) {

        if (authorDto.getId()!=null) {
            authorRepository.findById(authorDto.getId()).ifPresent(author -> {
                throw new DuplicatedEntityException("Entity Author with id " + author.getId() + " already exists");
            });
        }

        Author author = modelMapper.map(authorDto, Author.class);
        return authorRepository.save(author).getId();
    }

    @Transactional
    public AuthorDto delete(Long authorId) {

        Author author = authorRepository.findById(authorId).orElseThrow(EntityNotFoundException::new);
        authorRepository.delete(author);
        return modelMapper.map(author, AuthorDto.class);
    }


    @Transactional(readOnly=true)
    public AuthorDto get(Long autherId) {
    	Author auther = authorRepository.findById(autherId)
                                        .orElseThrow(EntityNotFoundException::new);
        return toDto(auther);
    }

    @Transactional(readOnly=true)
    public List<AuthorDto> list() {
        return authorRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    private AuthorDto toDto(Author author) {

        return modelMapper.map(author, AuthorDto.class);
    }
    
}
