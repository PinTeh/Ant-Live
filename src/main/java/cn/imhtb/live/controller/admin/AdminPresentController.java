package cn.imhtb.live.controller.admin;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.pojo.database.Present;
import cn.imhtb.live.modules.live.service.IPresentService;
import cn.imhtb.live.pojo.vo.request.PresentRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author PinTeh
 * @date 2020/5/5
 */
@RestController
@RequestMapping("/admin/present")
@PreAuthorize("hasAnyRole('ROLE_ROOT')")
public class AdminPresentController {

    private final IPresentService presentService;

    public AdminPresentController(IPresentService presentService) {
        this.presentService = presentService;
    }

    @PostMapping("/del")
    public ApiResponse del(Integer id) {
        try {
            presentService.removeById(id);
            return ApiResponse.ofSuccess("Delete success");
        } catch (Exception e) {
            return ApiResponse.ofSuccess("Delete fail");
        }
    }

    @PostMapping("/save")
    public ApiResponse save(@RequestBody PresentRequest request) {
        Present present = new Present();
        BeanUtils.copyProperties(request, present);
        presentService.save(present);
        return ApiResponse.ofSuccess("Save success");
    }

    @PostMapping("/edit")
    public ApiResponse edit(@RequestBody Present present) {
        presentService.updateById(present);
        return ApiResponse.ofSuccess("Edit success");
    }

    @GetMapping("/list")
    public ApiResponse presentList(@RequestParam(defaultValue = "1", required = false) Integer page, @RequestParam(defaultValue = "10", required = false) Integer limit) {
        IPage<Present> iPage = presentService.page(new Page<>(page, limit), new QueryWrapper<Present>().orderByDesc("sort", "id"));
        return ApiResponse.ofSuccess(iPage);
    }
}
