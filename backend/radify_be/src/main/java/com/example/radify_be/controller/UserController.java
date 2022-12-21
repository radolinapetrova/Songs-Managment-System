package com.example.radify_be.controller;

import com.example.radify_be.bussines.UserService;
import com.example.radify_be.controller.requests.UpdateUserRequest;
import com.example.radify_be.domain.Account;
import com.example.radify_be.domain.Role;
import com.example.radify_be.domain.User;
import com.example.radify_be.controller.requests.CreateUserRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@CrossOrigin("http://localhost:3000")
@AllArgsConstructor
public class UserController {


    private final UserService userService;


    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest userRequest) {
        try {
            userService.register(converter(userRequest));
            return ResponseEntity.ok().body("Account created");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }

    }


    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable(value = "id") Integer id){
        try{
            userService.deleteUser(id);
            return ResponseEntity.ok().body("Successful deletion of user");
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity getUser(@PathVariable(value = "id")Integer id){
        return ResponseEntity.ok().body(userService.getById(id));
    }

    @PutMapping
    public ResponseEntity updateUser(@RequestBody UpdateUserRequest request){
        return null;
    }


    private User converter(CreateUserRequest request){
        return User.builder()
                .account(Account.builder().email(request.getEmail()).password(request.getPassword()).username(request.getUsername()).build())
                .fName(request.getFirst_name()).lName(request.getLast_name()).role(Role.USER).build();
    }


}
