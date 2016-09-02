package hence.com.pumpkinweibo.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mxn.soul.flowingdrawer_core.MenuFragment;

import hence.com.pumpkinweibo.R;

import static hence.com.pumpkinweibo.WeiboActivity.LOG_TAG;

/**
 * Created by Hence on 2016/8/28.
 */

public class MyMenuFragment extends MenuFragment implements View.OnClickListener{
    @Override
    public void onClick(View view) {

    }

    private OnNavigationItemSelectedListener mCallback;

    public interface OnNavigationItemSelectedListener{
        public void onMenuselected(int postion);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = context instanceof Activity ? (Activity) context : null;
        try {
            mCallback = (OnNavigationItemSelectedListener) activity;
        }catch (Exception e){

        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        Log.d(LOG_TAG,"menu update ");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container,
                false);
        Log.d(LOG_TAG,"menu fragment ");

        return setupReveal(view);
    }
}
