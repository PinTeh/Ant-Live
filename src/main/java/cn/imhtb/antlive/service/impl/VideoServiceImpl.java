package cn.imhtb.antlive.service.impl;

import cn.imhtb.antlive.entity.database.Video;
import cn.imhtb.antlive.mappers.VideoMapper;
import cn.imhtb.antlive.service.IVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author PinTeh
 * @date 2020/5/25
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements IVideoService {
}
