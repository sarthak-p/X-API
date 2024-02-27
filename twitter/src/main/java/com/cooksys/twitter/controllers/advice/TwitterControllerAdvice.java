package com.cooksys.twitter.controllers.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackages = { "com.cooksys.twitter.controllers" })
@ResponseBody
public class TwitterControllerAdvice {
}
