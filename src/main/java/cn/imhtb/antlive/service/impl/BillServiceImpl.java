package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.entity.Bill;
import cn.imhtb.antlive.mappers.BillMapper;
import cn.imhtb.antlive.service.IBillService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements IBillService {
}
