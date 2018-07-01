package com.example.suman.intelligentsurveymanagement.activity.com.example.suman.intelligentsurveymanagement.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.suman.intelligentsurveymanagement.R;
import com.example.suman.intelligentsurveymanagement.activity.com.example.suman.intelligentsurveymanagement.dummy.LeftPanelContent;
import com.example.suman.intelligentsurveymanagement.activity.com.example.suman.intelligentsurveymanagement.fragments.CustomerSignOffFragment;
import com.example.suman.intelligentsurveymanagement.activity.com.example.suman.intelligentsurveymanagement.fragments.EvaluatingWorkFragment;
import com.example.suman.intelligentsurveymanagement.activity.com.example.suman.intelligentsurveymanagement.fragments.FormFragment;
import com.example.suman.intelligentsurveymanagement.activity.com.example.suman.intelligentsurveymanagement.fragments.LeftFragment;
import com.example.suman.intelligentsurveymanagement.activity.com.example.suman.intelligentsurveymanagement.fragments.SiteInformationFragment;
import com.example.suman.intelligentsurveymanagement.activity.com.example.suman.intelligentsurveymanagement.fragments.WorkStepsAndHazardsFragment;

public class DigitalFormActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LeftFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.digital_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_frame, FormFragment.newInstance("test", "test2"));
            //transaction.replace(R.id.main_frame, LeftFragment.newInstance(1));
            transaction.addToBackStack(FormFragment.TAG);
            transaction.commit();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(LeftPanelContent.DummyItem item) {
        switch (item.id) {
            case R.layout.fragment_site_information:
                SiteInformationFragment siteInformationFragment = SiteInformationFragment.newInstance("", "");
                FrameLayout fl = (FrameLayout) findViewById(R.id.right_panel);
                fl.removeAllViews();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.right_panel, siteInformationFragment);
                transaction.commit();
                break;
            case R.layout.fragment_evaluating_work:
                EvaluatingWorkFragment evaluatingWorkFragment = EvaluatingWorkFragment.newInstance("", "");
                fl = (FrameLayout) findViewById(R.id.right_panel);
                fl.removeAllViews();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.right_panel, evaluatingWorkFragment);
                transaction.commit();
                break;
            case R.layout.fragment_work_steps_and_hazards:
                WorkStepsAndHazardsFragment workStepsAndHazardsFragment = WorkStepsAndHazardsFragment.newInstance("", "");
                fl = (FrameLayout) findViewById(R.id.right_panel);
                fl.removeAllViews();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.right_panel, workStepsAndHazardsFragment);
                transaction.commit();
                break;
            case R.layout.fragment_customer_sign_off:
                CustomerSignOffFragment customerSignOffFragment = CustomerSignOffFragment.newInstance("", "");
                fl = (FrameLayout) findViewById(R.id.right_panel);
                fl.removeAllViews();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.right_panel, customerSignOffFragment);
                transaction.commit();
                break;
        }

    }
}
