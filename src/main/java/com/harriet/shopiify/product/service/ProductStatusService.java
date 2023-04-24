package com.harriet.shopiify.product.service;

import com.harriet.shopiify.product.dto.ProductStatusDTO;
import com.harriet.shopiify.product.model.ProductStatus;
import com.harriet.shopiify.product.repository.ProductStatusRepository;
import com.harriet.shopiify.product.vo.ProductStatusVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductStatusService {

    private final ProductStatusRepository productStatusRepository;

    public List<ProductStatusDTO> findAll (){
        return productStatusRepository.findAll().stream().map(bean-> toDTO(bean)).collect(Collectors.toList());
    }

    public void save (ProductStatusVO vo){
        productStatusRepository.save(toEntity(vo));
    }

    public ProductStatusDTO findById(Long Id){
        return toDTO(productStatusRepository.findById(Id).get());
    }

    public void updateById(Long Id, String productStatus){
        ProductStatus entity = productStatusRepository.findById(Id).get();
        entity.setStatus(productStatus);
    };

    public void deleteById(Long Id){
        productStatusRepository.deleteById(Id);
    }

    private ProductStatusDTO toDTO (ProductStatus original){
        ProductStatusDTO dto = new ProductStatusDTO();
        BeanUtils.copyProperties(original, dto);
        return dto;
    }

    private ProductStatus toEntity(ProductStatusVO vo){
        ProductStatus entity = new ProductStatus();
        BeanUtils.copyProperties(vo, entity);
        return entity;
    }
}
