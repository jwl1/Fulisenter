package cn.ucai.fulisenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.ucai.fulisenter.R;
import cn.ucai.fulisenter.controller.application.FuLiCenterApplication;
import cn.ucai.fulisenter.model.bean.MessageBean;
import cn.ucai.fulisenter.model.bean.User;
import cn.ucai.fulisenter.model.net.IModelUser;
import cn.ucai.fulisenter.model.net.ModeUser;
import cn.ucai.fulisenter.model.net.OnCompletionListener;
import cn.ucai.fulisenter.model.utils.ImageLoader;
import cn.ucai.fulisenter.model.utils.L;
import cn.ucai.fulisenter.view.MFGT;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalCenterFragment extends Fragment {
    private static final String TAG = PersonalCenterFragment.class.getSimpleName();

    @BindView(R.id.iv_user_Avatar)
    ImageView mIvUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    Unbinder unbinder;
    IModelUser modelUser;
    @BindView(R.id.tv_collrct_number)
    TextView tvCollrctNumber;
    @BindView(R.id.tv_shop_number)
    TextView tvShopNumber;
    @BindView(R.id.tv_trials_number)
    TextView tvTrialsNumber;

    public PersonalCenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_personal_center, container, false);
        unbinder = ButterKnife.bind(this, layout);
        inintData();
       getCollectCount();
        return layout;
    }

    private void inintData() {
        User user = FuLiCenterApplication.getUser();
        if (user != null) {
            loadUserInfo(user);
        } else {
            //MFGT.gotoLogin(getActivity());
        }
    }

    private void loadUserInfo(User user) {
        // ImageLoader.downloadImg(getContext(), mIvUserAvatar, user.getAvatarPath());
        ImageLoader.setAvatar(ImageLoader.getAvatarUrl(user), getContext(), mIvUserAvatar);
        mTvUserName.setText(user.getMuserNick());
        loadCollectCount("0");
    }

    private void loadCollectCount(String count) {
        L.e(TAG,"msg="+count);
        tvCollrctNumber.setText(String.valueOf(count));
    }
    private void getCollectCount(){
        User user = FuLiCenterApplication.getUser();
        L.e(TAG,"user="+user);
        modelUser = new ModeUser();
        modelUser.collectCount(getContext(), FuLiCenterApplication.getUser().getMuserName(),
                new OnCompletionListener<MessageBean>() {
                    @Override
                    public void onSuccess(MessageBean result) {
                        if (result != null && result.isSuccess()) {
                            loadCollectCount(result.getMsg());
                        }else {
                            loadCollectCount("0");
                        }
                    }

                    @Override
                    public void onError(String error) {
                        loadCollectCount("0");
                        L.e(TAG,"error="+error);
                    }
                });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_user_Avatar, R.id.tv_user_name, R.id.tv_set,R.id.ll_personal_collect})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_Avatar:
            case R.id.tv_user_name:
            case R.id.tv_set:
                if (FuLiCenterApplication.getUser() != null) {
                    MFGT.gotoSettings(getActivity());
                } else {
                    MFGT.gotoLogin(getActivity());
                }
                break;
            case R.id.ll_personal_collect:
                gotocollect();
        }
    }

    private void gotocollect() {
        MFGT.gotoColletace(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        inintData();
    }
}
