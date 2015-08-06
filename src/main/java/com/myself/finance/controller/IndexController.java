package com.myself.finance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController extends BaseController {

	@RequestMapping("")
	public String page() {
		return "index";
	}
}
