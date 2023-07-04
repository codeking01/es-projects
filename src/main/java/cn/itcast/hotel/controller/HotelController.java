package cn.itcast.hotel.controller;

import cn.itcast.hotel.customUtils.RepeatSubmit;
import cn.itcast.hotel.exception.CustomException;
import cn.itcast.hotel.pojo.PageResult;
import cn.itcast.hotel.pojo.RequestParams;
import cn.itcast.hotel.service.IHotelService;
import cn.itcast.hotel.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private IHotelService hotelService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RedisTemplate redisTemplate;

    // 搜索酒店数据
    @PostMapping("/list")
    public PageResult search(@RequestBody RequestParams params) {
        return hotelService.search(params);
    }

    @PostMapping("filters")
    public Map<String, List<String>> getFilters(@RequestBody RequestParams params) {
        return hotelService.getFilters(params);
    }

    @GetMapping("suggestion")
    public List<String> getSuggestions(@RequestParam("key") String prefix) {
        return hotelService.getSuggestions(prefix);
    }


    @PostMapping("/form")
    public void showForm(@RequestParam("id") Long id) {
        // 生成Token
        tokenService.createToken(id);
    }

    @RepeatSubmit
    @PostMapping("submit")
    public String submit(@RequestParam("id") Long id,@RequestBody String data) {
        try {
            // 校验 Token
            //tokenService.checkToken(1L);
            // 执行业务逻辑
            // ...
            System.out.println("success");
            return "success";
        } catch (CustomException ex) {
            return ex.getMessage();
        } catch (Exception ex) {
            return "error";
        }
    }
}