package hence.com.pumpkinweibo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.mxn.soul.flowingdrawer_core.FlowingView;
import com.mxn.soul.flowingdrawer_core.LeftDrawerLayout;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.utils.LogUtil;
import com.wang.avi.AVLoadingIndicatorView;
import com.yalantis.taurus.PullToRefreshView;


import hence.com.pumpkinweibo.Adapter.weiboAdapter;
import hence.com.pumpkinweibo.Fragment.MyMenuFragment;
import hence.com.pumpkinweibo.Network.VolleySingleton;
import hence.com.pumpkinweibo.Weibo.AccessTokenKeeper;
import hence.com.pumpkinweibo.Weibo.Constants;
import hence.com.pumpkinweibo.Weibo.ErrorInfo;
import hence.com.pumpkinweibo.Weibo.StatusList;
import hence.com.pumpkinweibo.Weibo.StatusesAPI;
import hence.com.pumpkinweibo.Weibo.WeiboStatus;

/**
 * Created by Hence on 2016/8/28.
 */

public class WeiboActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    public static final String LOG_TAG = "Log";
    private LeftDrawerLayout mLeftDrawerLayout;
    private MyMenuFragment mMenuFragment;
    private VolleySingleton volleySignleton;
    private RequestQueue requestQueue;
    private NavigationView mNav;
    private weiboAdapter weiboAdapter;
    private static String TAG = "TAG";
    private Context context;
    private Activity activity;
    private PullToRefreshView mPullToRefreshView;

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private LinearLayoutManager mLayoutManager;

    StatusList statuses;
    char loadingflag=' ';

  //  private AVLoadingIndicatorView avi;

    /**
     * 当前 Token 信息
     */
    private Oauth2AccessToken mAccessToken;
    /**
     * 用于获取微博信息流等操作的API
     */
    private StatusesAPI mStatusesAPI;

    public static final int REFRESH_DELAY = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weibo_main);
        context = this;
        activity = this;
    //    avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        mNav = (NavigationView) findViewById(R.id.vNavigation);
        mLeftDrawerLayout = (LeftDrawerLayout) findViewById(R.id.id_drawerlayout);
        FragmentManager fm = getSupportFragmentManager();
        mMenuFragment = (MyMenuFragment) fm.findFragmentById(R.id.id_container_menu);
        FlowingView mFlowingView = (FlowingView) findViewById(R.id.sv);
        if (mMenuFragment == null) {
            fm.beginTransaction().add(R.id.id_container_menu, mMenuFragment = new MyMenuFragment()).commit();

        }
        mLeftDrawerLayout.setFluidView(mFlowingView);
        mLeftDrawerLayout.setMenuFragment(mMenuFragment);

        mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                        loadingflag='T';
                        loading=false;
                        loadMoreWeibo(loadingflag);
                    }
                }, REFRESH_DELAY);
            }
        });

        getStatus();

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.weibomainrecyclerview);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                    //            Log.d(LOG_TAG, "dx and dy!" +dx+dy);
                    //             Log.d(LOG_TAG, "conter" +visibleItemCount+"/"+totalItemCount+"/"+pastVisiblesItems+"/");
                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            loadingflag='B';
                            Log.d(LOG_TAG, "Last Item Wow !");
                            loadMoreWeibo(loadingflag);
  //                          avi.show();

                            //Do pagination.. i.e. fetch new data
                        }
                    }

                }
            }
        });
    }

    public void getStatus() {
        // 获取当前已保存过的 Token
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
        // 对statusAPI实例化
        mStatusesAPI = new StatusesAPI(this, Constants.APP_KEY, mAccessToken);
        mStatusesAPI.friendsTimeline(0L, 0L, 10, 1, false, 0, false, mListener);
    }

    private void loadMoreWeibo(char loadingflag) {
        if (loadingflag == 'B') {
            long lastid = Long.valueOf(statuses.statusList.get(statuses.statusList.size() - 1).id);
            Log.d(LOG_TAG, "load more from" + lastid);
            mStatusesAPI.friendsTimeline(0L, lastid, 10, 1, false, 0, false, mListener);

        }else{
            long firstid = Long.valueOf(statuses.statusList.get(0).id);
            Log.d(LOG_TAG, "load more from" + firstid);
            mStatusesAPI.friendsTimeline(firstid, 0L, 10, 1, false, 0, false, mListener);
        }

    }


    private RequestListener mListener = new RequestListener() {
        @Override
        public void onComplete(String response) {
            if (!TextUtils.isEmpty(response)) {
                LogUtil.i(TAG, response);
                if (response.startsWith("{\"statuses\"")) {
                    // 调用 StatusList#parse 解析字符串成微博列表对象
                    if (!loading) {
                        if (loadingflag=='B') {
                            Log.d(LOG_TAG,"loading from bottom");
                            statuses = StatusList.parse(response);
                            weiboAdapter.addData(statuses, 'B');
 //                           avi.hide();

                        }else if (loadingflag=='T'){
                            Log.d(LOG_TAG,"loading from top");
                            statuses = StatusList.parse(response);
                            if (statuses.statusList!=null) {
                                weiboAdapter.addData(statuses, 'T');
                            }else {
                                Log.d(LOG_TAG,"loading from top but null return");
                                Snackbar.make(activity.getWindow().getDecorView(), "没有发现新的微博", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                        loading = true;
                    } else {
                        statuses = StatusList.parse(response);
                        weiboAdapter = new weiboAdapter(context, activity, statuses);
                        recyclerView.setAdapter(weiboAdapter);
                    }

                    if (statuses != null && statuses.total_number > 0) {

      //                  Toast.makeText(WeiboActivity.this,
    //                            "获取微博信息流成功, 条数: " + statuses.statusList.size(),
     //                           Toast.LENGTH_LONG).show();
                    }
                } else if (response.startsWith("{\"created_at\"")) {
                    // 调用 Status#parse 解析字符串成微博对象
                    WeiboStatus status = WeiboStatus.parse(response);
                    Toast.makeText(WeiboActivity.this,
                            "发送一送微博成功, id = " + status.id,
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(WeiboActivity.this, response, Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            LogUtil.e(TAG, e.getMessage());
            ErrorInfo info = ErrorInfo.parse(e.getMessage());
            Toast.makeText(WeiboActivity.this, info.toString(), Toast.LENGTH_LONG).show();
        }
    };
}
