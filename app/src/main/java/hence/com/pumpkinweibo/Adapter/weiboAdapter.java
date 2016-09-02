package hence.com.pumpkinweibo.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

import hence.com.pumpkinweibo.Network.ImageDownloaderTask;
import hence.com.pumpkinweibo.Network.VolleySingleton;
import hence.com.pumpkinweibo.R;
import hence.com.pumpkinweibo.Weibo.StatusList;
import hence.com.pumpkinweibo.Weibo.WeiboStatus;


/**
 * Created by Hence on 2016/8/29.
 */

public class weiboAdapter extends RecyclerSwipeAdapter<weiboAdapter.weiboViewholder> {

    private LayoutInflater layoutInflater;
    private ArrayList<WeiboStatus> statusArrayList;
    private Context context;
    private Activity activity;


    //VolleySingleton

    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private int ImageUrlCnt;


    public static final String LOG_TAG = "Log";


    @Override
    public weiboViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.weibo_status_layout, parent, false);
        weiboViewholder viewholder = new weiboViewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(final weiboViewholder holder, final int position) {

        //   Log.d(LOG_TAG,"rebuild Recycler View n"+ position);
        Log.d(LOG_TAG, "weibo status pos" + position);
        Log.d(LOG_TAG, "weibo status pos detail" + statusArrayList.get(position).id);
        Log.d(LOG_TAG, "weibo status img thumbnail " + statusArrayList.get(position).thumbnail_pic);
        if (statusArrayList.get(position).pic_urls != null) {
            Log.d(LOG_TAG, "weibo status img pid " + statusArrayList.get(position).pic_urls.toString());
        }
        holder.statusdetail.setText(statusArrayList.get(position).text);

        holder.statustime.setText(statusArrayList.get(position).created_at);
        holder.statususername.setText(statusArrayList.get(position).user.screen_name);

        String url = statusArrayList.get(position).user.profile_image_url;
        if (url != null) {
            imageLoader.get(url, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    //                 Log.d(LOG_TAG, "reponsesuccs img");
                    holder.statususerimg.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
        }
        ArrayList<String> imgUrlList = new ArrayList<String>();
        imgUrlList = statusArrayList.get(position).pic_urls;
        if (imgUrlList != null) {
            for (ImageUrlCnt = 0; ImageUrlCnt < imgUrlList.size(); ImageUrlCnt++) {

                String imgurl=imgUrlList.get(ImageUrlCnt).replace("thumbnail","bmiddle");

                        switch (ImageUrlCnt) {
                            case 0:
                                new ImageDownloaderTask(holder.statusimg1).execute(imgurl);
                                break;
                            case 1:
                                new ImageDownloaderTask(holder.statusimg2).execute(imgurl);
                                break;
                            case 2:
                                new ImageDownloaderTask(holder.statusimg3).execute(imgurl);
                                break;
                            case 3:
                                new ImageDownloaderTask(holder.statusimg4).execute(imgurl);
                                break;
                            case 4:
                                new ImageDownloaderTask(holder.statusimg5).execute(imgurl);
                                break;
                            case 5:
                                new ImageDownloaderTask(holder.statusimg6).execute(imgurl);
                                break;
                            case 6:
                                new ImageDownloaderTask(holder.statusimg7).execute(imgurl);
                                break;
                            case 7:
                                new ImageDownloaderTask(holder.statusimg8).execute(imgurl);
                                break;
                            case 8:
                                new ImageDownloaderTask(holder.statusimg9).execute(imgurl);
                                break;

                        }



            }
        }


    }

    public void loadStatusImage(String url) {

    }

    public void onUpdate() {

        notifyDataSetChanged();
    }

    public void addData(StatusList statusList, char loadingflag) {
        if (loadingflag == 'B') {
            int lastcnt = statusArrayList.size();
            int newcnt = statusArrayList.size();
            ArrayList<WeiboStatus> newStatusList = statusList.statusList;
            for (int i = 0; i < newStatusList.size(); i++) {
                statusArrayList.add(newStatusList.get(i));
                newcnt++;

            }
            notifyItemRangeInserted(lastcnt, newcnt);
        } else if (loadingflag == 'T') {

            boolean endflag = false;
            boolean noupdate = false;
            ArrayList<WeiboStatus> newStatusList = statusList.statusList;
            if (newStatusList != null) {
                long lastid = Long.valueOf(statusArrayList.get(0).id);
                for (int i = 0, j = 0; i < newStatusList.size() && endflag; i++) {
                    if (Long.valueOf(newStatusList.get(i).id) > lastid) {
                        statusArrayList.add(j, newStatusList.get(i));
                        j++;
                        noupdate = true;
                    } else {
                        endflag = true;
                    }
                }
                if (noupdate) {
                    notifyDataSetChanged();
                } else {
                    Snackbar.make(activity.getWindow().getDecorView(), "没有发现新的微博", Snackbar.LENGTH_SHORT).show();
                }
            } else {
                Snackbar.make(activity.getWindow().getDecorView(), "没有发现新的微博", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public int getItemCount() {
        //      Log.d(LOG_TAG, "size of weibo" + statusList.statusList.size());

        return statusArrayList.size();

    }


    public weiboAdapter(Context context, Activity activity, StatusList statusList) {
        //     Log.d(LOG_TAG,"Color adapter first"+data.size());


        this.context = context;
        statusArrayList = statusList.statusList;
        volleySingleton = volleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
        this.context = context;
        this.activity = activity;

        layoutInflater = LayoutInflater.from(context);
        //      colorlist=new int[weatherArrayList.size()];
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swap_item;
    }


    public class weiboViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView statusdetail;
        private TextView statustime;
        private TextView statususername;
        private ImageView statususerimg;
        private ImageView statusimg1;
        private ImageView statusimg2;
        private ImageView statusimg3;
        private ImageView statusimg4;
        private ImageView statusimg5;
        private ImageView statusimg6;
        private ImageView statusimg7;
        private ImageView statusimg8;
        private ImageView statusimg9;


        public weiboViewholder(View itemView) {
            super(itemView);

            statusdetail = (TextView) itemView.findViewById(R.id.weibosatus);
            statustime = (TextView) itemView.findViewById(R.id.weibotime);
            statususername = (TextView) itemView.findViewById(R.id.statususername);
            statususerimg = (ImageView) itemView.findViewById(R.id.statususerimg);
            statusimg1 = (ImageView) itemView.findViewById(R.id.img1);
            statusimg2 = (ImageView) itemView.findViewById(R.id.img2);
            statusimg3 = (ImageView) itemView.findViewById(R.id.img3);
            statusimg4 = (ImageView) itemView.findViewById(R.id.img4);
            statusimg5 = (ImageView) itemView.findViewById(R.id.img5);
            statusimg6 = (ImageView) itemView.findViewById(R.id.img6);
            statusimg7 = (ImageView) itemView.findViewById(R.id.img7);
            statusimg8 = (ImageView) itemView.findViewById(R.id.img8);
            statusimg9 = (ImageView) itemView.findViewById(R.id.img9);
        }

        @Override
        public void onClick(View v) {


        }
    }

}