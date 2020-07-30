package mywork.toolkit;



import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;


@RestController
@Api(tags = "用于演示的接口")
@RequestMapping("/demo")
public class DemoController {


    @GetMapping("/square")
    @ApiOperation("square")
    @ApiImplicitParam(name = "x", value = "平方的数值", defaultValue = "1", required = true)
	public long square(@RequestParam(value = "x", defaultValue = "1") long x) {
		return x * x;
    }
    
    
}