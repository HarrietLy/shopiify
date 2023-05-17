package com.harriet.shopiify.auth.service;

import com.harriet.shopiify.auth.dto.ShippingAddressDTO;
import com.harriet.shopiify.auth.model.ShippingAddress;
import com.harriet.shopiify.auth.repository.ShippingAddressRepository;
import com.harriet.shopiify.auth.vo.ShippingAddressUpdateVO;
import com.harriet.shopiify.auth.vo.ShippingAddressVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShippingAddressService {
    private final ShippingAddressRepository shippingAddressRepository;
    public ShippingAddressService (ShippingAddressRepository shippingAddressRepository){
        this.shippingAddressRepository=shippingAddressRepository;
    }
    public Long addShippingAddress (ShippingAddressVO vo){
        ShippingAddress entity = new ShippingAddress();
        entity.setShippingAddress(vo.getShippingAddress());
        entity.setUserId(vo.getUserId());
        return shippingAddressRepository.save(entity).getId();
    }

    public void updateShippingAddress(Long id, ShippingAddressUpdateVO vo){
        ShippingAddress addressToUpdate = shippingAddressRepository.findById(id).get();
        addressToUpdate.setShippingAddress(vo.getShippingAddress());
        shippingAddressRepository.save(addressToUpdate);
    }

    public void deleteShippingAddress (long id){
        shippingAddressRepository.deleteById(id);
    }

    public List<ShippingAddressDTO> findShippingAddressByUserId(Long userId){
        List<ShippingAddress> entities = shippingAddressRepository.findByUserId(userId);
        List<ShippingAddressDTO> dtos = new ArrayList<>();
        entities.forEach( entity ->{
            ShippingAddressDTO dto = new ShippingAddressDTO();
            BeanUtils.copyProperties(entities,dto);
            dtos.add(dto);
        });
        return dtos;
    }
}

