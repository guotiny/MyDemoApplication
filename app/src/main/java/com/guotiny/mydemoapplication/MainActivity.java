package com.guotiny.mydemoapplication;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.guotiny.mydemoapplication.magicviewpagerindicator.IndicatiorMainActivity;
import com.guotiny.mydemoapplication.takeimage.activity.TakeImagesMainActivity;


public class MainActivity extends ListActivity {


    private static class DemoDetails {
        private final int titleId;
        private final int descriptionId;
        private final Class<? extends android.app.Activity> activityClass;

        public DemoDetails(int titleId, int descriptionId,
                           Class<? extends android.app.Activity> activityClass) {
            super();
            this.titleId = titleId;
            this.descriptionId = descriptionId;
            this.activityClass = activityClass;
        }
    }


    private static class CustomArrayAdapter extends ArrayAdapter<DemoDetails> {
        public CustomArrayAdapter(Context context, DemoDetails[] demos) {
            super(context, R.layout.feature, R.id.title, demos);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FeatureView featureView;
            if (convertView instanceof FeatureView) {
                featureView = (FeatureView) convertView;
            } else {
                featureView = new FeatureView(getContext());
            }
            DemoDetails demo = getItem(position);
            featureView.setTitleId(demo.titleId);
            featureView.setDescriptionId(demo.descriptionId);
            return featureView;
        }
    }

    private static final DemoDetails[] demos = {
            //评论添加图片，多图片选择浏览器
            new DemoDetails(R.string.takePhoto, R.string.takePhoto,
                    TakeImagesMainActivity.class),//
            //仿今日头条顶部导航指示器，
            new DemoDetails(R.string.indicator,R.string.indicatorMore,
                    IndicatiorMainActivity.class),
            new DemoDetails(R.string.loadingLikeEle,R.string.loadingLikeEleMore,
                    LoadingViewLikeELEActivity.class),
            new DemoDetails(R.string.miCalendar,R.string.miCalendarMore,
                    MiCalendarActivity.class)
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        ListAdapter adapter = new CustomArrayAdapter(
                this.getApplicationContext(), demos);
        setListAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        DemoDetails demo = (DemoDetails) getListAdapter().getItem(position);
        startActivity(new Intent(this.getApplicationContext(),
                demo.activityClass));
    }


}
