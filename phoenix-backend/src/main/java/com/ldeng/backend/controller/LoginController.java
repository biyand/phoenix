package com.ldeng.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldeng.backend.fr.openam.AMUserService;
import com.ldeng.backend.model.User;
import com.ldeng.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private AMUserService amUserService;

//    @RequestMapping("/login")
//    public ResponseEntity login(
//            @RequestParam(value="error", required = false) String error
//    ) {
//        if (error != null) {
//            return new ResponseEntity("Login failed.",HttpStatus.BAD_REQUEST);
//        }
//
//        return new ResponseEntity("Login success.",HttpStatus.OK);
//    }

    @RequestMapping("/")
    public ResponseEntity logout(
            @RequestParam("logout") String logout
    ){
        return new ResponseEntity("Logout success.", HttpStatus.OK);
    }

//    @RequestMapping(value="login", method = RequestMethod.POST)
//    public String loginPost(
//            @RequestBody Map<String, String> json,
//            HttpServletRequest request
//    ) throws
//            ServletException {
//        if(json.get("username") == null || json.get("password") ==null) {
//            throw new ServletException("Please fill in username and password");
//        }
//
//        String username = json.get("username");
//        String password = json.get("password");
//
//        User user= userService.findByUsername(username);
//        if (user==null) {
//            throw new ServletException("User name not found.");
//        }
//
//        String pwd = user.getPassword();
//
//        if(!password.equals(pwd)) {
//            throw new ServletException("Invalid login. Please check your name and password");
//        }
//
//
//        return "login success";
//    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.POST)
    public String logout() {
        SecurityContextHolder.clearContext();

        return "logout success.";
    }

//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    public User registerUser(@RequestBody User user) {
//        return userService.save(user);
//    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public void authenticate(
            @RequestBody HashMap<String, Object> mapper
    ){
        ObjectMapper om = new ObjectMapper();
        String username = (String) mapper.get("username");
        String password = (String) mapper.get("password");

        amUserService.authenticateUser(username, password);
    }

    @RequestMapping("/test")
    public String test() {
        return "test";
    }

    @RequestMapping("/checkSession")
    public String checkSession() {
        return "UserSession Active";
    }

    @RequestMapping("/token")
    @ResponseBody
    public Map<String,String> token(HttpSession session, HttpServletRequest request) {
        System.out.println(request.getRemoteHost());

        String remoteHost = request.getRemoteHost();
        int portNumber = request.getRemotePort();

        System.out.println(remoteHost+":"+portNumber);
        System.out.println(request.getRemoteAddr());

        return Collections.singletonMap("token", session.getId());
    }
}
