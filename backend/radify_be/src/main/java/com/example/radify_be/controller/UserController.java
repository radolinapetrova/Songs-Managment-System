package com.example.radify_be.controller;

import com.example.radify_be.bussines.UserService;
import com.example.radify_be.bussines.exceptions.DublicateDataException;
import com.example.radify_be.bussines.exceptions.InvalidInputException;
import com.example.radify_be.bussines.exceptions.UnsuccessfulAction;
import com.example.radify_be.controller.requests.UpdateUserRequest;
import com.example.radify_be.controller.requests.UserResponse;
import com.example.radify_be.domain.Account;
import com.example.radify_be.domain.Role;
import com.example.radify_be.domain.User;
import com.example.radify_be.controller.requests.CreateUserRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@CrossOrigin("http://localhost:3000")
@AllArgsConstructor
@Slf4j
public class UserController {


    private final UserService userService;


    @PostMapping
    public ResponseEntity createUser(@RequestBody CreateUserRequest userRequest) {
        try {
            return ResponseEntity.ok().body(userService.register(converter(userRequest)));
        }
        catch (InvalidInputException e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
        catch(DublicateDataException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }


    }

    @PutMapping("/account")
    @ResponseBody
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserRequest request){
        UserResponse user = null;
        try{
            user = responseConverter(userService.updateUser(updateConverter(request)));
        }
        catch(UnsuccessfulAction e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
        catch (InvalidInputException ex){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex.getMessage());
        }

        return ResponseEntity.ok().body(user.toString());
    }


    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable(value = "id") Integer id){
        log.info("hereee");
        try{
            userService.deleteUser(id);
            return ResponseEntity.ok().body("Successful deletion of user");
        }
        catch(UnsuccessfulAction e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id")Integer id){
        return ResponseEntity.ok().body(userService.getById(id));
    }




    private User converter(CreateUserRequest request){
        return User.builder()
                .account(Account.builder().email(request.getEmail()).password(request.getPassword()).username(request.getUsername()).build())
                .fName(request.getFirst_name()).lName(request.getLast_name()).role(Role.USER).build();
    }

    private User updateConverter(UpdateUserRequest request){
        return User.builder().id(request.getId()).account(Account.builder().email(request.getEmail()).username(request.getUsername()).build()).fName(request.getFirst_name()).lName(request.getLast_name()).build();
    }

    private UserResponse responseConverter(User user){
            return UserResponse.builder().fName(user.getFName()).lName(user.getLName()).email(user.getAccount().getEmail()).username(user.getAccount().getUsername()).build();
    }


}
