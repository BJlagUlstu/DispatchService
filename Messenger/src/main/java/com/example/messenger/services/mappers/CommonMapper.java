package com.example.messenger.services.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommonMapper {

    @Autowired
    private ModelMapper modelMapper;

    public <T, S> S convertToEntity(T dto, Class<S> type) {
        return modelMapper.map(dto, type);
    }

    public <T, S> S convertToDto(T entity, Class<S> type) {
        return modelMapper.map(entity, type);
    }

    public <T, S> List<S> convertToList(List<T> list, Class<S> type) {
        return list.stream()
                .map(data -> convertToDto(data, type))
                .toList();
    }
}
