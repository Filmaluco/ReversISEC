package com.example.rmcsilva.reverisectest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class GameInfoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView tvNavTitle;
    TextView tvNavContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        tvNavTitle = findViewById(R.id.tvNavTitle);
        tvNavContent = findViewById(R.id.tvNavContent);

        tvNavContent.setMovementMethod(new ScrollingMovementMethod());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navItemAbout) {
            tvNavTitle.setText(getString(R.string.navMenuAbout));
            tvNavContent.setText(getString(R.string.aboutMenuContent));
        } else if (id == R.id.navItemAuthors) {
            tvNavTitle.setText(getString(R.string.navMenuAuthors));
            tvNavContent.setText(getString(R.string.authorsMenuContent));
        } else if (id == R.id.navItemFeatures) {
            tvNavTitle.setText(getString(R.string.navMenuFeatures));
            tvNavContent.setText(getString(R.string.featuresMenuContent));
        } else if (id == R.id.navItemOptions) {
            tvNavTitle.setText(getString(R.string.navMenuOptions));
            tvNavContent.setText(getString(R.string.optionsMenuContent));
        } else if (id == R.id.navItemRules) {
            tvNavTitle.setText(getString(R.string.navMenuRules));
            tvNavContent.setText(getString(R.string.rulesMenuContent));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
