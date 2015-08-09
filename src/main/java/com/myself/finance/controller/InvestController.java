package com.myself.finance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myself.common.message.JsonResult;
import com.myself.finance.data.UserProductData;
import com.myself.finance.page.IPage;
import com.myself.finance.param.UserProductQueryParam;
import com.myself.finance.service.IUserProductService;

@Controller
@RequestMapping("/invest")
public class InvestController extends BaseController {

	@Autowired
	private IUserProductService userProductService;
	
	@RequestMapping("/record/{productId}")
	@ResponseBody
	public Object record(@PathVariable String productId, UserProductQueryParam param) {
		param.setProductId(productId);
		IPage<UserProductData> datas = userProductService.query(param);
		
		JsonResult<UserProductData> jResult = new JsonResult<UserProductData>();
		jResult.setDraw(param.getDraw());
		jResult.setRecordsTotal(datas.getTotalRecord());
		jResult.setRecordsFiltered(datas.getTotalRecord());
		jResult.setData((List<UserProductData>)datas.getData());
		return jResult;
	}
}
