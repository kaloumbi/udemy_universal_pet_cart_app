package com.doyoucode.universal_pet_car.dto;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EntityConverter <T, D> {

    private final ModelMapper modelMapper;

    public D mapEntityToDto(T entity, Class<D> dtoClass){
        return modelMapper.map(entity, dtoClass);
    }

    public T mapDtoToEntity(D dtoClass, Class<T> entityClass){
        return modelMapper.map(dtoClass, entityClass);
    }
}
