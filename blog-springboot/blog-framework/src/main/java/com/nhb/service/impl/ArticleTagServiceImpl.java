package com.nhb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhb.domain.entity.ArticleTag;
import com.nhb.mapper.ArticleTagMapper;
import com.nhb.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author 大只
 * @since 2022-10-03 17:11:41
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}
