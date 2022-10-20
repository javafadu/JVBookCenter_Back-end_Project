package com.library.controller;

import com.library.domain.User;
import com.library.dto.UserDTO;
import com.library.dto.request.UpdateUserRequest;
import com.library.dto.response.LResponse;
import com.library.dto.response.UserResponse;
import com.library.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;


    @GetMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<Page<UserResponse>> getAllUsersByPage(@RequestParam("page") int page,
                                                           @RequestParam("size") int size,
                                                           @RequestParam("sort") String prop,
                                                           @RequestParam ("direction") Direction direction) {

        Pageable pageable= PageRequest.of(page, size, Sort.by(direction,prop));

        Page<UserResponse> usersWithPage = userService.getUserPage(pageable);
        return  ResponseEntity.ok(usersWithPage);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
        UserResponse user = userService.findById(id);
        return ResponseEntity.ok(user);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') ")
    public ResponseEntity<UserResponse> updateUser (HttpServletRequest httpServletRequest,@Valid @PathVariable Long id, @RequestBody UpdateUserRequest updateUserRequest){
        Long updaterId =(Long) httpServletRequest.getAttribute("id");

        UserResponse userResponse= userService.userUpdate(updaterId, id, updateUserRequest);

        return ResponseEntity.ok(userResponse);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') ")
    public ResponseEntity<UserResponse> deleteUser ( @PathVariable Long id){

        UserResponse userResponse= userService.deleteUser(id);

        return ResponseEntity.ok(userResponse);

    }
}
