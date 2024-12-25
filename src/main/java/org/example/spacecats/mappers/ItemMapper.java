package org.example.spacecats.mappers;

import org.example.spacecats.dto.ItemRequestDTO;
import org.example.spacecats.dto.ItemResponseDTO;
import org.example.spacecats.entities.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ItemMapper {

    public Product toEntity(ItemRequestDTO dto) {
        Product product = new Product();
        product.setItemName(dto.getName());
        product.setItemDescription(dto.getDescription());
        product.setItemCost(dto.getPrice());
        return product;
    }

    public ItemResponseDTO toResponseDTO(Product product) {
        ItemResponseDTO response = new ItemResponseDTO();
        response.setId(product.getItemId());
        response.setName(product.getItemName());
        response.setDescription(product.getItemDescription());
        response.setPrice(product.getItemCost());
        return response;
    }

    public List<ItemResponseDTO> toResponseDTOList(List<Product> products) {
        return products.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }
}
