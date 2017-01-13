package cn.ucai.fulisenter.model.net;

import android.content.Context;

import cn.ucai.fulisenter.controller.application.I;
import cn.ucai.fulisenter.model.bean.CategoryChildBean;
import cn.ucai.fulisenter.model.bean.CategoryGroupBean;

import cn.ucai.fulisenter.model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class ModelCategory implements IModelNewCategory {
    @Override
    public void downData(Context context, OnCompletionListener<CategoryGroupBean[]> listener) {
        OkHttpUtils<CategoryGroupBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_GROUP)
                .targetClass(CategoryGroupBean[].class)
                .execute(listener);
    }


    @Override
    public void downData(Context context, int parentId, OnCompletionListener<CategoryChildBean[]> listener) {
        OkHttpUtils<CategoryChildBean[]> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_FIND_CATEGORY_CHILDREN)
                .addParam(I.CategoryChild.PARENT_ID,String.valueOf(parentId))
                .targetClass(CategoryChildBean[].class)
                .execute(listener);
    }
}