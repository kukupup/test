package com.tulingxueyuan.mall.controller;

import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.dto.OssPolicyResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsSkuStock;
import com.tulingxueyuan.mall.modules.pms.service.PmsSkuStockService;
import com.tulingxueyuan.mall.service.impl.OssServiceImpl;
import com.tulingxueyuan.mall.vo.ResultURL;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 *
 * '/aliyun/oss/policy'
 */
@Controller
@Api(tags = "OssController", description = "Oss管理")
@RequestMapping("/aliyun/oss")
public class OssController {
    @Autowired
    private OssServiceImpl ossService;
    @Autowired
    private PmsSkuStockService pmsSkuStockService;

     //测试修改增加注解url地址 githup上传
    @ApiOperation(value = "oss上传签名生成")
    @RequestMapping(value = "/policy", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<OssPolicyResult> policy() {
        OssPolicyResult result = ossService.policy();
        return CommonResult.success(result);
    }
    //执行成功后
    @RequestMapping(value = "/Success",method = RequestMethod.GET)
    @ResponseBody
    public CommonResult success(@ApiParam(value = "url地址")@RequestParam(value = "url") String url){
        System.out.println(url);
        ResultURL resultURL=new ResultURL();
        resultURL.setUrl(url);
       return CommonResult.success("Downurl");

    }
    //添加注释
    private String testUpload;
    

}
