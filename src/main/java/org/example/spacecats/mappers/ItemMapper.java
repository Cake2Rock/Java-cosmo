package org.example.spacecats.mappers;

import org.example.spacecats.domain.Product;
import org.example.spacecats.dto.CreateItemRequest;
import org.example.spacecats.dto.ItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    @Mapping(target = "id", ignore = true)
    Product toDomain(CreateItemRequest dto);

    ItemResponse toResponse(Product product);

    List<ItemResponse> toResponseList(List<Product> products);
}
