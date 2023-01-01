package com.example.radify_be.controller;


import com.example.radify_be.bussines.ListenersService;
import com.example.radify_be.controller.requests.AddListenerRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/listeners")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class ListenersController {

    private final ListenersService service;

    @GetMapping("{id}")
    public ResponseEntity getMonthlyListeners(@PathVariable (value = "id") Integer id){
        return ResponseEntity.ok().body(service.getMonthlyListeners(id));
    }

    @GetMapping("year/{id}")
    public ResponseEntity getYearlyListeners(@PathVariable (value = "id") Integer id){
        return ResponseEntity.ok().body(service.getYearlyListeners(id));
    }



    @PostMapping
    public ResponseEntity addListener(@RequestBody AddListenerRequest request){
        service.save(request.getSong(), request.getUser());
        return ResponseEntity.ok().body("Mi good job ig");
    }

    @GetMapping("/top")
    public ResponseEntity getTopSongs(){
        return ResponseEntity.ok().body( service.getTopSongs());
    }
}
