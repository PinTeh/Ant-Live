package cn.imhtb.antlive.controller.admin;

import cn.imhtb.antlive.common.ApiResponse;
import cn.imhtb.antlive.entity.Present;
import cn.imhtb.antlive.service.IPresentService;
import cn.imhtb.antlive.vo.request.PresentRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final ModelMapper modelMapper;

    public AdminPresentController(IPresentService presentService, ModelMapper modelMapper) {
        this.presentService = presentService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/del")
    public ApiResponse del(Integer id){
        try {
            presentService.removeById(id);
            return ApiResponse.ofSuccess("Delete success");
        }catch (Exception e){
            return ApiResponse.ofSuccess("Delete fail");
        }
    }

    @PostMapping("/save")
    public ApiResponse save(@RequestBody PresentRequest request){
        Present present = modelMapper.map(request, Present.class);
        presentService.save(present);
        return ApiResponse.ofSuccess("Save success");
    }

    @PostMapping("/edit")
    public ApiResponse edit(@RequestBody Present present){
        presentService.updateById(present);
        return ApiResponse.ofSuccess("Edit success");
    }

    @GetMapping("/list")
    public ApiResponse presentList(@RequestParam(defaultValue = "1",required = false) Integer page,@RequestParam(defaultValue = "10",required = false) Integer limit){
        IPage<Present> iPage = presentService.page(new Page<>(page,limit), new QueryWrapper<Present>().orderByDesc("sort","id"));
        return ApiResponse.ofSuccess(iPage);
    }
}
