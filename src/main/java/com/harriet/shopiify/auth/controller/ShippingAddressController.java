package com.harriet.shopiify.auth.controller;

import com.harriet.shopiify.auth.dto.ShippingAddressDTO;
import com.harriet.shopiify.auth.service.ShippingAddressService;
import com.harriet.shopiify.auth.vo.ShippingAddressUpdateVO;
import com.harriet.shopiify.auth.vo.ShippingAddressVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ShippingAddressController {
    private final ShippingAddressService shippingAddressService;
    public ShippingAddressController(ShippingAddressService shippingAddressService) {
        this.shippingAddressService = shippingAddressService;
    }

    @PostMapping
    public Long addShippingAddress(@RequestBody @Valid ShippingAddressVO vo){
        return shippingAddressService.addShippingAddress(vo);
    }

    @GetMapping("/{userId}")
    public List<ShippingAddressDTO> getShippingAddressByUserId(@PathVariable("userId") Long userId){
        return shippingAddressService.findShippingAddByUserId(userId);
    }

    @PutMapping("/{id}")
    public void updateShippingAddress(@PathVariable("id") Long id, @RequestBody ShippingAddressUpdateVO vo){
        shippingAddressService.updateShippingAddress(id,vo);
    }

    @DeleteMapping("/{id}")
    public void deleteShippingAddress(@PathVariable("id") Long id){
        shippingAddressService.deleteShippingAddress(id);
    }
}
