package cn.imhtb.live.modules.system.controller;

import cn.imhtb.live.common.ApiResponse;
import cn.imhtb.live.common.PageData;
import cn.imhtb.live.modules.infra.controller.AbstractBaseController;
import cn.imhtb.live.modules.infra.model.PageQuery;
import cn.imhtb.live.modules.live.service.IMessageService;
import cn.imhtb.live.modules.system.model.SystemMessageDetail;
import cn.imhtb.live.modules.system.model.SystemMessageQuery;
import cn.imhtb.live.modules.system.model.SystemMessageUpdate;
import cn.imhtb.live.modules.system.service.ISystemMessageService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pinteh
 * @date 2025/4/29
 */
@Api(tags = "系统_消息接口")
@RequestMapping("/api/v1/system/message")
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SystemMessageController extends AbstractBaseController<ISystemMessageService, SystemMessageDetail, SystemMessageQuery, SystemMessageDetail, SystemMessageUpdate> {

    private final IMessageService messageService;

    @GetMapping("/pageDetail")
    public ApiResponse<PageData<SystemMessageDetail>> pageDetail(SystemMessageQuery query, PageQuery pageQuery) {
        PageData<SystemMessageDetail> pageData = messageService.pageDetail(pageQuery, query);
        return ApiResponse.ofSuccess(pageData);
    }

}
