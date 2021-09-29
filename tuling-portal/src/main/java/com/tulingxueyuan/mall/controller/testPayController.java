package com.tulingxueyuan.mall.controller;/*
 * className:testPayController
 * package:com.tulingxueyuan.mall.controller
 * Descriptions:
 * @Date:2021/9/1517:48
 * @Author:zhangmin@163.com
 */
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.tulingxueyuan.mall.dto.OrderDetailDTO;
import com.tulingxueyuan.mall.modules.oms.model.OmsOrderItem;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
@RestController
public class testPayController {

//
//    private final String APP_ID = "应用的APPID";
//    private final String APP_PRIVATE_KEY = "生成的应用私钥";
//    private final String CHARSET = "UTF-8";
//    private final String ALIPAY_PUBLIC_KEY = "支付宝公钥";
//    //这是沙箱接口路径,正式路径为https://openapi.alipay.com/gateway.do
//    private final String GATEWAY_URL ="https://openapi.alipaydev.com/gateway.do";
//    private final String FORMAT = "JSON";
//    //签名方式
//    private final String SIGN_TYPE = "RSA2";
//    //支付宝异步通知路径,付款完毕后会异步调用本项目的方法,必须为公网地址
//    private final String NOTIFY_URL = "http://公网地址/notifyUrl";
//    //支付宝同步通知路径,也就是当付款完毕后跳转本项目的页面,可以不是公网地址
//    private final String RETURN_URL = "http://公网地址/returnUrl";





    private final String APP_ID = "2021000118617879";
    private final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCfQ/8KX0RxFpRrTz3p6KO+EP4Ywu2JtGK5Eq4c+VO9K5cmS/G4Fg0UqeemenacI+OEluNN3ZUbNL0Q99lp6lvDNELQC20dL9nQv+ZTlC4VcasWZKHiB7dukFhmdpiqWikwQHBk7Ti/oJ6e/jQZfY3p2MzdLB0tBRnn08bFC9Xw74GJQwIj1jb0PZ3AtGSj9TeKzqTZjsfsRR58Iy+Dkn3eqAeY09Qug3glbwng0FZ5V3fleUXWbSvVi2mYtMYcqa3WLxGFeJwNof3+65tjWmZUlz/ZBDSn838ZbbeK9w5XQswvzuDlpdW7LaPW63V/mAP5hJy0kzW8ch3VZDLc2PChAgMBAAECggEACGiKmn2VG3NZuqCgYC0MGAjFoYEJEXAYljTKRHVVWnNms3rm9BUV87VUqZFlmbC7XlCpB39fU+Rg5vhu3gylc5TawikYRaY1FbRL8LWuCSvLII+HjGtj3853HFG+lZ/U9NsyE+ICUPul5Db/gSkDyQjNKBAgKjROvdKMd/K8BNBK3e11FwWswIIXO7J5/3TFMxVb3F1RWxDCjAFRDPRyprG/SF/ct4IeM3fgFqXusSLlG/pIEsKiiUf1+jmrFQp16ORQ7T4rrdCLt8pF/CHqyY2jiXSY+8boliUYBTRhvYg4JJSaZmzt0pLdhSbgy9bMhTc/qSM3rUNThLOyi7xIwQKBgQDbLnOg7hUxym9WxtiWJnlj/h1Zu9bNEokRTZrtnqHmuNGsl8bd0UZvZXL19apDd5+NiyQ5a4sBekX8lNdbCkT/5fApr/bt9IxRzjW3szYoEQS8PsR1GAvGd1oJQ0xyKL+OXP+AUEx219BYX290dyAQS9zhkkFwkrrZygAgbgTDvQKBgQC6BPWhZX5ygTKf7QGQMmcPo5ZwkyAHQJHoIYU4QXZkAuKjptkW9zh36UO7Pd3DKwjXMwP0En0mOCn0/fjYX9GrBt6WCRG9yN1T+KC+z9DdOvVILs7POE+S+BM5gQ09oX5h3uOT2vKoBogzfkMJainLIa+oKhjjBMug+M3IfId8tQKBgQDXmlsNvAVHRD2/m7yj/ta8QNT4VykV8xy306IKClhyT+2ZHHf37QufOqkaP30r/rkJkAyjGyHS40eDDZrGKFT1cGeNalW5fyykr/bxbC1ivlLtJcU09eFnajI8GGJTjbfYv6xpuRNueB6vP7mwVBpLTYTKs4cByKRa5jS3seGn9QKBgFbvImw/Jo0U3DfpZ6WgKJIifZg26ez5vDakE8JqtY2f25Nn0mTtPLKtjT3gd9dSMVuBOAPE8EAAV3trhfR5M1+eYj/0FRqA2Q75TwZd0NrTRvZpCbXGN0oULcfeC/YVlx7eL7VVybnE87o8889ekhGguJr6sY7HGvMr0+Ayco9RAoGAPT16yx4E3WQeAP5tVI2BEcssrU0si0IS5BCp/stoVkUsyR9Bu1vJTC6VwVZUNEzy48ado1Zof5oHpQCHEP037LFkwK9TmynW4S1G13vF6N6h2n/Fn69xm6qcvPvUXc/E2vwEexurXADY5rRqE2yeKvlGc+KoLSHrUkbSCtfY2+o=";
    private final String CHARSET = "UTF-8";
    private final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhBY02ZNA1SEP2qnT+6j3Z8ZVyw3MFmbL2gXG9Bhj2Ttes8OPeP1ZPBNTIRuKvS+qsL2aq6sFMpjBJgFsr0Px6zJ7mqzEHqzEvEJoh8P2Pc5UWkLxpvwBgc0ghUUp6CbIo3qUOcVHd/HcJkj40PHSAJtgmahhPgZ5Lao76SD4LkLiGPy8sMY3TRy/p+JqYXdjn9WXtSiAJOetvNW10Q3gCkMwWBHNhry0s4UV0LfO278uU5gNAjmz4civOSJ0RaObOlmbPLnN/A6rXIfBJqn25NgmPY5Un6d5bKrmzCVQHFTh9r66JYHwJMxJpPnQ3GjOq+xBQ3Qxs2jYorUbVi3d8wIDAQAB";
    //这是沙箱接口路径,正式路径为https://openapi.alipay.com/gateway.do
    private final String GATEWAY_URL ="https://openapi.alipaydev.com/gateway.do";
    private final String FORMAT = "JSON";
    //签名方式
    private final String SIGN_TYPE = "RSA2";
    //支付宝异步通知路径,付款完毕后会异步调用本项目的方法,必须为公网地址
    private final String NOTIFY_URL = "http://127.0.0.1/notifyUrl";
    //支付宝同步通知路径,也就是当付款完毕后跳转本项目的页面,可以不是公网地址
    private final String RETURN_URL = "http://1e11-180-106-50-18.ngrok.io/returnUrl";



    @RequestMapping("/aliapy")
    public void alipay(HttpServletResponse httpResponse) throws IOException {

        SecureRandom r= new SecureRandom();
        //实例化客户端,填入所需参数
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        //在公共参数中设置回跳和通知地址
        request.setReturnUrl(RETURN_URL);
        request.setNotifyUrl(NOTIFY_URL);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        //生成随机Id
        String out_trade_no = UUID.randomUUID().toString();
       // String out_trade_no = detail.getId().toString();
        //付款金额，必填
        String total_amount =Integer.toString(r.nextInt(1)+9999);
       // String total_amount= detail.getPayAmount().toString();
        //订单名称，必填

        StringBuilder subject=new StringBuilder();

//        for (OmsOrderItem omsOrderItem : detail.getOrderItemList()) {
//
//             subject.append(omsOrderItem.getProductName())  ;
//        }
        subject.append("名称");

        //商品描述，可空
     //   String body = "购买商品"+detail.getOrderItemList().size()+"件共"+total_amount+"元";
        String body="商品";
        request.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();


    }
    @RequestMapping(value = "/returnUrl", method = RequestMethod.GET)
    public String returnUrl(HttpServletRequest request, HttpServletResponse response)
            throws IOException, AlipayApiException {
        System.out.println("=================================同步回调=====================================");

        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("utf-8"), "utf-8");
            params.put(name, valueStr);
        }

        System.out.println(params);//查看参数都有哪些
        boolean signVerified = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE); // 调用SDK验证签名
        //验证签名通过
        if(signVerified){
            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

            System.out.println("商户订单号="+out_trade_no);
            System.out.println("支付宝交易号="+trade_no);
            System.out.println("付款金额="+total_amount);

            //支付成功，修复支付状态
           // payService.updateById(Integer.valueOf(out_trade_no));
            return "支付成功"+"商户订单号="+out_trade_no;//跳转付款成功页面
        }else{
            return "no";//跳转付款失败页面
        }

    }

}
