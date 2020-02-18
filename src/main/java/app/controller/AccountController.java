package app.controller;


import app.handler.exception.InvalidRequestException;
import app.model.form.PilotForm;
import app.response.ErrorResponse;
import app.response.LoginResponse;
import app.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/account")
public class AccountController {

    @Resource
    AccountService accountService;

    @PostMapping("/login")
    private ResponseEntity<?> login(@RequestBody @Valid PilotForm pilotForm, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            throw new InvalidRequestException(bindingResult.getFieldErrors());
        if(!confirmPassword(pilotForm.getPassword(), pilotForm.getRetypePassword()))
           return ResponseEntity.badRequest().body(new ErrorResponse("Two passwords are not equal."));

        return ResponseEntity.ok().body(new LoginResponse("token",pilotForm.getPilotId()));
    }

    private boolean confirmPassword(String password, String retypePassword){
        return password.equals(retypePassword);
    }

}
