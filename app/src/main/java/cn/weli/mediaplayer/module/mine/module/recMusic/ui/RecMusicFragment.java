package cn.weli.mediaplayer.module.mine.module.recMusic.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import cn.weli.mediaplayer.R;
import cn.weli.mediaplayer.adapter.MusicRecyleViewAdapter;
import cn.weli.mediaplayer.base.BaseFragment;
import cn.weli.mediaplayer.bean.SongData;
import cn.weli.mediaplayer.constant.SongsConstant;
import cn.weli.mediaplayer.helper.BroadcastHelper;
import cn.weli.mediaplayer.module.mine.module.recMusic.presenter.RecMusicPresenter;
import cn.weli.mediaplayer.module.mine.module.recMusic.view.IRecMusicView;

public class RecMusicFragment extends BaseFragment<RecMusicPresenter, IRecMusicView> implements IRecMusicView {

    private View mFragmentView;
    private RecyclerView mRecyclerView;
    private MusicRecyleViewAdapter adapter;
    private boolean isFragmentInit = false;
    private TextView mLoadTxt;

    @Override
    protected Class<RecMusicPresenter> getPresenterClass() {
        return RecMusicPresenter.class;
    }

    @Override
    protected Class<IRecMusicView> getViewClass() {
        return IRecMusicView.class;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mFragmentView == null) {
            mFragmentView = inflater.inflate(R.layout.music_layout, container, false);
            ButterKnife.bind(this, mFragmentView);
            initView();
        } else {
            if (mFragmentView.getParent() != null) {
                ((ViewGroup) mFragmentView.getParent()).removeView(mFragmentView);
            }
        }
        return mFragmentView;
    }


    /**
     * ?????????
     */
    private void initView() {

        mRecyclerView = mFragmentView.findViewById(R.id.recy_music_list);
        mLoadTxt = mFragmentView.findViewById(R.id.load_txt);
        //??????recyleView??????????????????????????????????????????RecylerView?????????
        mRecyclerView.setHasFixedSize(true);
        //????????????
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //??????recyleView????????????
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        //???recyleView?????????????????????
        mRecyclerView.setLayoutManager(layoutManager);

        mPresenter.obtainRecSongs();

        isFragmentInit = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {    //????????????
        }
    }

    @Override
    public void setAdapter(List<SongData> list) {

        if(list.size() > 0){
            mLoadTxt.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }else {
            mLoadTxt.setText("????????????????????????...");
        }


        adapter = new MusicRecyleViewAdapter(list, SongsConstant.TYPE_REC);
        mRecyclerView.setAdapter(adapter);

        //????????????
        adapter.setOnItemClickListener(new MusicRecyleViewAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(SongData songData, int position) {
                mPresenter.dealItemClick(songData, position);
            }

            @Override
            public void onItemLongClick(SongData songData) {

            }

            @Override
            public void hasFav(SongData songData) {
            }
        });
    }

    @Override
    public void setData(List<SongData> list) {
        adapter.setSongList(list);
    }

}
