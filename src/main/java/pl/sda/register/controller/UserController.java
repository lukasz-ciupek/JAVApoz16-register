package pl.sda.register.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.register.exception.DuplicatedUsernameException;
import pl.sda.register.model.User;
import pl.sda.register.service.UserService;

import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ModelAndView usersListView(@RequestParam(required = false) String firstName, @RequestParam(required = false) boolean exactMatch) {//TODO: task if firstName is not null, filter via it (url structure: /users?firstName=test)
        ModelAndView modelAndView = new ModelAndView("users");
        modelAndView.addObject("users", userService.findAllUserNames(firstName,exactMatch));
        return modelAndView;
    }

    @GetMapping("/users/{username}")
    public ModelAndView userDetailsView(@PathVariable String username) {
        ModelAndView modelAndView = new ModelAndView("userDetails");
        modelAndView.addObject("user", userService.findUserByUserName(username));
        return modelAndView;
    }

    @GetMapping("users/delete/{username}")
    public String removeUser(@PathVariable String username) {
        userService.deleteUser(username);
        return "redirect:/users";
    }

    @GetMapping("/user/add")
    public ModelAndView createUserView() {
        ModelAndView modelAndView = new ModelAndView("addUser");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/user")
    public String addUser(@ModelAttribute User user) {
        //TODO: task is to add user to repository
        userService.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("/users/search")
    public ModelAndView searchUsersByName() {
        return new ModelAndView("searchUser");
       }

    @GetMapping("/user/update/{username}")
    public ModelAndView updateUserView(@PathVariable String username) {
        ModelAndView modelAndView = new ModelAndView("addUser");
        modelAndView.addObject("user", userService.findUserByUserName(username));
        modelAndView.addObject("update",true);
        return modelAndView;
    }

    @PostMapping("/user/update")
    public String updateUser(@ModelAttribute User user)
    {
        userService.updateUser(user);
        return "redirect:/users/" + user.getUsername();
    }
}
