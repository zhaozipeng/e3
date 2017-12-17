package com.e3.service.content.service.impl;

import com.e3.service.content.mapper.TbContentCategoryMapper;
import com.e3.service.content.mapper.TbContentMapper;
import com.e3.service.content.pojo.TbContent;
import com.e3.service.content.pojo.TbContentCategory;
import com.e3.service.content.pojo.TbContentCategoryExample;
import com.e3.service.content.pojo.TbContentExample;
import com.e3.service.content.service.ContentService;
import com.e3.utils.E3Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/11/23.
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Resource
    TbContentCategoryMapper tbContentCategoryMapper;
    @Resource
    TbContentMapper tbContentMapper;
    @Override
    public List<TbContentCategory> selectCatoryList(Long id) {


        TbContentCategoryExample example=new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(id);

        return tbContentCategoryMapper.selectByExample(example);
    }

    @Override
    public E3Result create(Long parentId, String name) {
        //1 创建一个TbContentCategory对象,补全其他信息
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setUpdated(new Date());
        tbContentCategory.setIsParent(false);
        tbContentCategory.setName(name);
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setStatus(1);
        tbContentCategory.setParentId(parentId);
        tbContentCategoryMapper.insert(tbContentCategory);
        //2 查询所添加的节点的IsParent
        //2.1 如果是，无所谓
        //2.2 修改状态为parent
        TbContentCategory example = new TbContentCategory();
        example.setId(parentId);
        example.setIsParent(true);
        tbContentCategoryMapper.updateByPrimaryKeySelective(example);
        return E3Result.ok(null);
    }


    // 删除  如果删除父节点，递归删除子节点
    @Override
    public E3Result delete(Long id) {
        //1.2 非叶子节点，先删掉子节点，再删掉自己
        //1.2.1 查询出子节点，再判断子节点是否是叶子节点
        deleteEveryOne(id);
        return E3Result.ok(null);
    }

    @Override
    public E3Result save(TbContent tbContent) {
        tbContent.setUpdated(new Date());
        tbContent.setCreated(new Date());
        tbContentMapper.insert(tbContent);
        return E3Result.ok(null);
    }

    @Override
    public List<TbContent> selectContentList(long i) {

        List<TbContent>tbContents=tbContentMapper.selectOrderByDate(i);

        return tbContents;
    }

    private void deleteEveryOne(Long id) {
        //查询出子节点
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(id);
        List<TbContentCategory> tbContentCategories = tbContentCategoryMapper.selectByExample(example);
        if (tbContentCategories != null && tbContentCategories.size() > 0) {
            for (TbContentCategory c : tbContentCategories
                    ) {
                if (!c.getIsParent()) {
                    //删除自己
                    tbContentCategoryMapper.deleteByPrimaryKey(c.getId());
                } else {
                    deleteEveryOne(c.getId());
                }
            }
            tbContentCategoryMapper.deleteByPrimaryKey(id);
        }else{
            tbContentCategoryMapper.deleteByPrimaryKey(id);
        }

    }
}
