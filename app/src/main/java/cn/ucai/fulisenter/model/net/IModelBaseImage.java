package cn.ucai.fulisenter.model.net;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public interface IModelBaseImage extends IModeBase {
    void releaseImage();
    void showImage(Context context, String url, ImageView imageView, int defaultPicId, boolean isDragging);
    void showImage(Context context, String url, int width, int height, ImageView imageView, int defaultPicId, boolean isDragging);
}

