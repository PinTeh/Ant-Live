package cn.imhtb.antlive.controller.admin;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.common.Constants;
import cn.imhtb.antlive.entity.User;
import cn.imhtb.antlive.service.IUserService;
import cn.imhtb.antlive.vo.request.IdsRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author PinTeh
 * @date 2020/5/9
 */
@RestController
@RequestMapping("/admin/user")
@PreAuthorize("hasAnyRole('ROLE_ROOT','ROLE_USER')")
public class AdminUserController {

    private final IUserService userService;

    public AdminUserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public ApiResponse userList(@RequestParam(defaultValue = "1",required = false) Integer page,
                                @RequestParam(defaultValue = "10",required = false) Integer limit,
                                @RequestParam(required = false)Integer uid,
                                @RequestParam(required = false)Integer disabled){
        IPage<User> iPage = userService.page(new Page<>(page,limit), new QueryWrapper<User>()
                .eq(uid!=null,"id",uid)
                .eq(disabled!=null,"disabled",disabled)
                .orderByDesc("id"));
        return ApiResponse.ofSuccess(iPage);
    }

    /**
     *  封禁用户
     */
    @PostMapping("/block/{type}")
    public ApiResponse userBlock(@RequestBody IdsRequest request,
                                 @PathVariable(name = "type") String type){
        Integer[] ids = request.getIds();
        if ("block".equals(type)){
            userService.updateStatusByIds(ids, Constants.DisabledStatus.NO.getCode());
        }else if ("unblock".equals(type)){
            userService.updateStatusByIds(ids, Constants.DisabledStatus.YES.getCode());
        }
        return ApiResponse.ofSuccess();
    }
}
