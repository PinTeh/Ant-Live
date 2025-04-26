package cn.imhtb.live.modules.infra.controller;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.common.PageData;
import cn.imhtb.live.modules.infra.model.PageQuery;
import cn.imhtb.live.modules.infra.model.SortQuery;
import cn.imhtb.live.modules.infra.service.IBaseService;
import cn.imhtb.live.pojo.vo.request.IdsRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * @author pinteh
 * @date 2025/4/12
 */
public class AbstractBaseController<S extends IBaseService<L, Q, D, U>, L, Q, D, U> {

    @Autowired
    private S baseService;

    @ApiOperation("获取详情数据")
    @GetMapping("/detail")
    public ApiResponse<D> list(@RequestParam Integer id) {
        return ApiResponse.ofSuccess(baseService.detail(id));
    }

    @ApiOperation("获取列表数据")
    @GetMapping("/list")
    public ApiResponse<List<L>> list(Q query, SortQuery sortQuery) {
        return ApiResponse.ofSuccess(baseService.list(query, sortQuery));
    }

    @ApiOperation("获取分页数据")
    @GetMapping("/page")
    public ApiResponse<PageData<L>> page(Q query, PageQuery pageQuery) {
        return ApiResponse.ofSuccess(baseService.page(query, pageQuery));
    }

    @ApiOperation("新增或更新数据")
    @PostMapping("/save")
    public ApiResponse<Boolean> addOrUpdate(@RequestBody @Valid U update) {
        return ApiResponse.ofSuccess(baseService.addOrUpdate(update));
    }

    @ApiOperation("批量删除数据")
    @PostMapping("/delete")
    public ApiResponse<Boolean> delete(@RequestBody @Valid IdsRequest request) {
        return ApiResponse.ofSuccess(baseService.delete(Arrays.asList(request.getIds())));
    }

}
