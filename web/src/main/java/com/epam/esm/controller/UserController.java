package com.epam.esm.controller;


import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.DTO.TagDTO;
import com.epam.esm.DTO.UserDTO;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final OrderService orderService;
    private final TagService tagService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserById(@PathVariable long id) {
        return userService.read(id);
    }

    @GetMapping("/{userId}/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTO> getUserOrders(@PathVariable
                                        @Positive long userId,
                                        @RequestParam(value = "page", required = false, defaultValue = "1")
                                        @Positive long page,
                                        @RequestParam(value = "size", required = false, defaultValue = "10")
                                        @Positive long size) {
        return orderService.getUserOrders(userId, page, size);
    }

    @GetMapping("/{userId}/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getUserOrder(@PathVariable
                                 @Positive long userId,
                                 @PathVariable
                                 @Positive long orderId) {
        return orderService.getUserOrder(userId, orderId);
    }

    @PostMapping("/{userId}/orders/{certificateId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO createOrder(@PathVariable
                                @Positive long userId,
                                @PathVariable
                                @Positive long certificateId) {
        return orderService.create(userId, certificateId);
    }

    @GetMapping("/{userId}/mostWidelyUsedTag")
    @ResponseStatus(HttpStatus.OK)
    public TagDTO getMostWidelyUsedTag(@PathVariable
                                       @Positive long userId) {
        return tagService.getMostWidelyUsedTag(userId);
    }
}
