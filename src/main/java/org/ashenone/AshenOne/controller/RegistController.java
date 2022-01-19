package org.ashenone.AshenOne.controller;

import org.ashenone.AshenOne.domain.User;
import org.ashenone.AshenOne.domain.dto.CaptchaResponseDto;
import org.ashenone.AshenOne.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistController
{
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    private UserService userService;

    @GetMapping("/regist")
    public String regist()
    {
        return "regist";
    }

    @Value("${recaptcha.secret}")
    private String recaptchaSecret;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/regist")
    public String addUser(@Valid User user,
                          BindingResult bindingResult,
                          @RequestParam String confirmPassword,
                          @RequestParam("g-recaptcha-response") String captchaResponse,
                          Model model)
    {
        String url = String.format(CAPTCHA_URL, recaptchaSecret, captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        if (!response.isSuccess())
            model.addAttribute("captchaError", "Fill captcha!");

        if (confirmPassword.isEmpty())
            model.addAttribute("confirmPasswordError", "Repeat password can not be empty!");

        if (user.getPassword() != null &&
                !user.getPassword().equals(confirmPassword))
        {
            model.addAttribute("passwordError", "Passwords are different!");
            return "regist";
        }

        if (confirmPassword.isEmpty()
                || !response.isSuccess()
                || bindingResult.hasErrors())
        {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            model.addAttribute("user", null);
            return "regist";
        }

        if (!userService.addUser(user))
        {
            model.addAttribute("usernameError", "User is already exists!");
            return "regist";
        }

        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code)
    {
        boolean isActivated = userService.activateUser(code);

        if (isActivated)
        {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated!");
        }
        else
        {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code is not found!");
        }

        return "login";
    }

//    @InitBinder
//    public void initBinder(WebDataBinder binder)
//    {
//        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
//    }
}
