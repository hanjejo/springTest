package com.example.demo;

import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/{Id}")
    public ResponseEntity<User> getUser(@PathVariable("Id") Long PathId,
                                        @PathParam("testId") Long ParamId) {
        // User user = userRepository.findById(PathId).orElse(null); // @PathVariable
        User user = userRepository.findById(ParamId).orElseThrow(); // @PathParam
        return ResponseEntity.ok(user);
    }

    @GetMapping()
    public List<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "홍길동") String username) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findByUsername(username, pageable).getContent();
    }

    @Transactional
    @PostMapping("/")
    public User createUser(@RequestBody User user) {
        User createdUser = userRepository.save(user);
        if (createdUser.getUsername().equals("홍길동"))
            throw new RuntimeException();
        if ((long) createdUser.getOrders().size() != 0)
            return null;

        for (Order order : createdUser.getOrders()) {
            order.setUser(createdUser);
            orderRepository.save(order);
        }

        return createdUser;
    }

    @PostMapping("/test/create")
    public void createUser() {
        User user = User.builder()
                .email("test@Email.com")
                .username("홍길동2")
                .build();

        userRepository.save(user);
    }
}