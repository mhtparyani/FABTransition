package com.linkindia.example;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.linkindia.fab_transformation.FabTransformation;
import com.linkindia.example.fabtransformation.R;

import butterknife.OnClick;

public class TransformToToolbarActivity extends AppCompatActivity {

    FloatingActionButton fab;
    View toolbarFooter;
    ScrollView scrollView;

    private boolean isTransforming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        initView();

    }
    int getLayoutResId() {
        return R.layout.activity_transform_to_toolbar;
    }

    protected void initView() {
        fab= findViewById(R.id.fab);
        toolbarFooter= findViewById(R.id.toolbar_footer);
        scrollView=findViewById(R.id.scroll_view);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(
                new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (fab.getVisibility() != View.VISIBLE && !isTransforming) {
                            FabTransformation.with(fab)
                                    .setListener(new FabTransformation.OnTransformListener() {
                                        @Override
                                        public void onStartTransform() {
                                            isTransforming = true;
                                        }

                                        @Override
                                        public void onEndTransform() {
                                            isTransforming = false;
                                        }
                                    })
                                    .transformFrom(toolbarFooter);
                        }
                    }
                });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fab.getVisibility() == View.VISIBLE) {
                    FabTransformation.with(fab).transformTo(toolbarFooter);
                }
            }
        });
    }

    @OnClick(R.id.fab)
    void onClickFab() {
        if (fab.getVisibility() == View.VISIBLE) {
            FabTransformation.with(fab).transformTo(toolbarFooter);
        }
    }

    @Override
    public void onBackPressed() {
        if (fab.getVisibility() != View.VISIBLE) {
            FabTransformation.with(fab).transformFrom(toolbarFooter);
            return;
        }
        super.onBackPressed();
    }
}
