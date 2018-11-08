package com.nsw.wx.product.repository;


import com.nsw.wx.product.common.WeChatProductOutput;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;


@Component
public interface TbWeChatProductRepository extends ElasticsearchRepository<WeChatProductOutput, Integer> {

}
