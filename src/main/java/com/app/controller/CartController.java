package com.app.controller;

import com.app.customExceptions.ResourceNotFoundException;
import com.app.customExceptions.UnexpectedErrorException;
import com.app.dto.ResponseDTO;
import com.app.pojo.Cart;
import com.app.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/cart")
public class CartController {

    @Autowired
    ICartService cartService;

    public CartController() {
        System.out.println("in CartController --  " + getClass().getName());
    }

    @GetMapping("/list/{user_id}")/*--------------------------------------------User getAllCartItemList Done-----------------------------------------------*/
    public ResponseDTO getAllCartItemList(@PathVariable String user_id) {
        System.out.println("in get all cart item list");
        return new ResponseDTO(true, cartService.getAllCartItems(Integer.parseInt(user_id)));
    }

    @PostMapping("/add")/*--------------------------------------------User addNewCartIem Done-----------------------------------------------*/
    public ResponseDTO addNewCartIem(@RequestBody Cart cartItem) {
        System.out.println("in add new cart item");
        Cart cart = cartService.addCartItem(cartItem);
        if (cart != null) {
            return new ResponseDTO(true, "Product added into cart successfully");
        }
        throw new UnexpectedErrorException("Error while adding new  product to cart");
    }

    @PutMapping("/update/{cart_id}/{cart_quantity}")/*--------------------------------------------User updateCartItemQty Done-----------------------------------------------*/
    public ResponseDTO updateCartItemQty(@PathVariable String cart_id, @PathVariable String cart_quantity) {
        System.out.println("in update  cart item quantity");
        Cart cart = cartService.updateCartItemQty(Integer.parseInt(cart_id), Integer.parseInt(cart_quantity));
        if (cart != null) {
            return new ResponseDTO(true, "Product Quantity updated  successfully");
        }
        throw new UnexpectedErrorException("Error while updating cart item");
    }

    @DeleteMapping("/delete/{cart_id}")/*--------------------------------------------User deleteCartItem Done-----------------------------------------------*/
    public ResponseDTO deleteCartItem(@PathVariable String cart_id) {
        System.out.println("in delete  cart item");
        return new ResponseDTO(true, cartService.deleteCartItem(Integer.parseInt(cart_id)));
    }

    @DeleteMapping("/deleteAll/{user_id}")
    public ResponseEntity deleteAllCartItemsByUser(@PathVariable String user_id) {
        System.out.println("in delete All  cart item");
        return new ResponseEntity(cartService.deleteAllCartItemByUser(Integer.parseInt(user_id)), HttpStatus.OK);
    }
}
