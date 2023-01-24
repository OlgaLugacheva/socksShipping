package com.example.socksshipping.dto;

import com.example.socksshipping.model.Sock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SocksMapper {

    Sock toSocks(SockShippingDto sockShippingDto);

}
