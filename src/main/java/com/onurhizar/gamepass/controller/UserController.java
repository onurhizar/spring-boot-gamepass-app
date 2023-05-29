package com.onurhizar.gamepass.controller;

import com.onurhizar.gamepass.model.request.CreateUserRequest;
import com.onurhizar.gamepass.model.response.InvoiceResponse;
import com.onurhizar.gamepass.model.response.UserResponse;
import com.onurhizar.gamepass.service.InvoiceService;
import com.onurhizar.gamepass.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final InvoiceService invoiceService;

    @GetMapping
    public List<UserResponse> listUsers(){
        return userService.listUsers().stream().map(UserResponse::fromEntity).collect(Collectors.toList());
    }

    @GetMapping("{userId}")
    public UserResponse getUser(@PathVariable String userId){
        return UserResponse.fromEntity(userService.findById(userId));
    }

    @PostMapping
    public UserResponse addUser(@Valid @RequestBody CreateUserRequest request){
        return UserResponse.fromEntity(userService.addUser(request));
    }

    @PutMapping("{userId}")
    public UserResponse updateUser(@Valid @RequestBody CreateUserRequest request, @PathVariable String userId){
        return UserResponse.fromEntity(userService.updateUser(userId, request));
    }

    @DeleteMapping("{userId}")
   public void deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
   }

   /** subscribe or upgrade to a subscription */
   @PostMapping("{userId}/subscribe/{subscriptionId}")
   public void subscribe(@PathVariable String userId, @PathVariable String subscriptionId){
        userService.subscribe(userId,subscriptionId);
   }

   @GetMapping("{userId}/invoice")
    public List<InvoiceResponse> getInvoicesOfUser(@PathVariable String userId){
          return invoiceService.getInvoicesOfUser(userId);
    }

}
