package org.yaojiu.supermarket.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.yaojiu.supermarket.entity.Good;
import org.yaojiu.supermarket.mapper.GoodMapper;
import org.yaojiu.supermarket.service.GoodService;

@Service
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good> implements GoodService {
}
