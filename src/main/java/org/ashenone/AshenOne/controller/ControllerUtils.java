package org.ashenone.AshenOne.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtils
{
    static Map<String, String> getErrors(BindingResult bindingResult)
    {
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );
        return bindingResult.getFieldErrors().stream().collect(collector);
    }

    static String redirectUpdatedPage(
            RedirectAttributes redirectAttributes,
            @RequestHeader(required = false) String referer
    ) {
        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();

        components.getQueryParams()
                .entrySet()
                .forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));

        return "redirect:" + components.getPath();
    }

//    static Map<String, String> getErrors(BindingResult bindingResult)
//    {
//        Map<String, String> result = new TreeMap<>();
//
//        for (FieldError f : bindingResult.getFieldErrors())
//        {
//            if (!result.containsKey(f.getField() + "Error"))
//            {
//                result.put(f.getField() + "Error", f.getDefaultMessage());
//            }
//        }
//        return result;
//    }
}