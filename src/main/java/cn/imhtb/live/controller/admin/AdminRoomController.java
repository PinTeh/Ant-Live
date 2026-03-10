package cn.imhtb.live.controller.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pinteh
 * @date 2025/12/7
 */
@RestController
@RequestMapping("/admin/user")
@PreAuthorize("hasAnyRole('ROLE_ROOT','ROLE_USER')")
public class AdminRoomController {



}
