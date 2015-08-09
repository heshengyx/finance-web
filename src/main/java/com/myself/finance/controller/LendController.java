package com.myself.finance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myself.common.message.JsonResult;
import com.myself.finance.entity.Product;
import com.myself.finance.page.IPage;
import com.myself.finance.param.ProductQueryParam;
import com.myself.finance.service.IProductService;

@Controller
@RequestMapping("/lend")
public class LendController extends BaseController {
	
	@Autowired
	private IProductService productService;
	
	@RequestMapping("")
	public String page() {
		return "lend";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public Object list(ProductQueryParam param) {
		IPage<Product> datas = productService.query(param);
		
		JsonResult<Product> jResult = new JsonResult<Product>();
		jResult.setDraw(param.getDraw());
		jResult.setRecordsTotal(datas.getTotalRecord());
		jResult.setRecordsFiltered(datas.getTotalRecord());
		jResult.setData((List<Product>)datas.getData());
		return jResult;
	}
	
	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable String id, Model model) {
		Product param = new Product();
		param.setId(id);
		param = productService.getData(param);
		model.addAttribute("product", param);
		return "lendDetail";
	}
}
