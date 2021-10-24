package com.epam.esm.controller;


import com.epam.esm.DTO.OrderDTO;
import com.epam.esm.DTO.TagDTO;
import com.epam.esm.DTO.UserDTO;
import com.epam.esm.assembler.OrderRepresentationModelAssembler;
import com.epam.esm.assembler.TagRepresentationModelAssembler;
import com.epam.esm.assembler.UserRepresentationModelAssembler;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final OrderService orderService;
    private final TagService tagService;
    private final UserRepresentationModelAssembler userAssembler;
    private final OrderRepresentationModelAssembler orderAssembler;
    private final TagRepresentationModelAssembler tagAssembler;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<UserDTO> getUserById(@PathVariable long id) {
        return userAssembler.toModel(userService.read(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<UserDTO> findAll(@RequestParam(value = "page", required = false, defaultValue = "1")
                                               long page,
                                       @RequestParam(value = "size", required = false, defaultValue = "10")
                                               long size) {
        PagedModel<UserDTO> userDTOS = userService.readAll(page, size);
        return userAssembler.toPagedModel(userDTOS);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<UserDTO> create(@RequestBody @Valid UserDTO user) {
        System.out.println(user);
        return userAssembler.toModel(userService.create(user));
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
    public EntityModel<OrderDTO> getUserOrder(@PathVariable
                                              @Positive long userId,
                                              @PathVariable
                                              @Positive long orderId) {
        return orderAssembler.toModel(orderService.getUserOrder(userId, orderId));
    }

    @PostMapping(value = "/{userId}/orders/{certificateId}", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<OrderDTO> createOrder(@PathVariable
                                             @Positive long userId,
                                             @PathVariable
                                             @Positive long certificateId) {
        return orderAssembler.toModel(orderService.create(userId, certificateId));
    }

    @GetMapping("/{userId}/mostWidelyUsedTag")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<TagDTO> getMostWidelyUsedTag(@PathVariable
                                                    @Positive long userId) {
        return tagAssembler.toModel(tagService.getMostWidelyUsedTag(userId));
    }
}
