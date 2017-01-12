package cn.ucai.fulisenter.model.net;

import android.content.Context;
import android.widget.ImageView;

import cn.ucai.fulisenter.model.utils.OkImageLoader;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class ModelBaseImage extends ModeBase implements IModelBaseImage {
    @Override
    public void releaseImage() {
        OkImageLoader.release();
    }

    @Override
    public void showImage(Context context, String url, ImageView imageView, int defaultPicId, boolean isDragging) {
        OkImageLoader.build(url)
                .imageView(imageView)
                .defaultPicture(defaultPicId)
                .setDragging(isDragging)
                .showImage(context);
    }

    @Override
    public void showImage(Context context, String url, int width, int height, ImageView imageView, int defaultPicId, boolean isDragging) {
        OkImageLoader.build(url)
                .imageView(imageView)
                .defaultPicture(defaultPicId)
                .width(width)
                .height(height)
                .setDragging(isDragging)
                .showImage(context);
    }
}
