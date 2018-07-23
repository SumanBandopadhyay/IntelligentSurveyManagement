package intelligentsurveymanagement.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.suman.intelligentsurveymanagement.R;
import intelligentsurveymanagement.database.AppDatabase;
import intelligentsurveymanagement.dummy.LeftPanelContent;
import intelligentsurveymanagement.entity.Form;
import intelligentsurveymanagement.executor.AppExecutors;
import intelligentsurveymanagement.fragments.CustomerSignOffFragment;
import intelligentsurveymanagement.fragments.DraftJobsFragment;
import intelligentsurveymanagement.fragments.EquipmentDetailsFragment;
import intelligentsurveymanagement.fragments.EvaluatingWorkFragment;
import intelligentsurveymanagement.fragments.FormFragment;
import intelligentsurveymanagement.fragments.HomeFragment;
import intelligentsurveymanagement.fragments.InboxJobsFragment;
import intelligentsurveymanagement.fragments.LeftFragment;
import intelligentsurveymanagement.fragments.SentJobsFragment;
import intelligentsurveymanagement.fragments.SiteInformationFragment;
import intelligentsurveymanagement.fragments.WorkStepsAndHazardsFragment;
import intelligentsurveymanagement.utils.DatabaseInitializer;

import java.util.List;

public class DigitalFormActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        LeftFragment.OnListFragmentInteractionListener,
        SentJobsFragment.OnListFragmentInteractionListener,
        InboxJobsFragment.OnListFragmentInteractionListener,
        DraftJobsFragment.OnListFragmentInteractionListener {

    public static final String DRAFT = "draft";
    public static final String INBOX = "inbox";
    public static final String SENT = "sent";
    public static List<Form> ALLFORMS;
    public static List<Form> SENTFORMS;
    public static List<Form> INBOXFORMS;
    public static List<Form> DRAFTFORMS;
    public static Form SELECTEDFORM;

    public static AppDatabase appDatabase;
    public static AppExecutors appExecutors;

    private static SharedPreferences sharedPreferences;
    private static boolean firstTimeAppLaunch;

    private static final String TAG = "DigitalForm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Jiffy");
        setSupportActionBar(toolbar);

        appExecutors = new AppExecutors();

        sharedPreferences = this.getPreferences(MODE_PRIVATE);
        firstTimeAppLaunch = sharedPreferences.getBoolean(getString(R.string.firstTimeAppLaunch), true);
        Log.e(TAG, firstTimeAppLaunch+"");

        initializeLists(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            Fragment fragment = new HomeFragment();
            transaction.replace(R.id.main_frame, fragment);
//            transaction.disallowAddToBackStack();
            transaction.commit();
//        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        navigationView.getMenu().performIdentifierAction(R.id.nav_home, 0);
//        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_home));
//        navigationView.getMenu().getItem(0).setChecked(true);
//        navigationView.setCheckedItem(R.id.nav_home);
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//        // Checks the orientation of the screen
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
//            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
//        }
//    }

    public static void initializeLists(Activity activity) {
        Log.e(TAG, "Initialize List");

        if (firstTimeAppLaunch) {
            DatabaseInitializer.populateAsync(appDatabase, appExecutors, activity.getApplicationContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(activity.getString(R.string.firstTimeAppLaunch), false);
            editor.commit();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        DatabaseInitializer.getAllJobs(appDatabase, appExecutors, activity.getApplicationContext());
        DatabaseInitializer.getSentJobs(appDatabase, appExecutors, activity.getApplicationContext());
        DatabaseInitializer.getInboxJobs(appDatabase, appExecutors, activity.getApplicationContext());
        DatabaseInitializer.getDraftJobs(appDatabase, appExecutors, activity.getApplicationContext());
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

        /*if (id == R.id.nav_digital_form) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main_frame, FormFragment.newInstance("intelligentsurveymanagement", "test2"));
            //transaction.replace(R.id.main_frame, LeftFragment.newInstance(1));
            transaction.addToBackStack(null);
            transaction.commit();
        } else */
        if (id == R.id.nav_home) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.main_frame, HomeFragment.newInstance("intelligentsurveymanagement", "test2"));
            //transaction.replace(R.id.main_frame, LeftFragment.newInstance(1));
            //transaction.addToBackStack(null);
            transaction.disallowAddToBackStack();
            transaction.commit();
        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_help) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // LeftPanelFragment listener method
    @Override
    public void onListFragmentInteraction(LeftPanelContent.DummyItem item) {
        switch (item.id) {
            case R.layout.fragment_site_information:
                Fragment fragment = new SiteInformationFragment();
                FrameLayout fl = (FrameLayout) findViewById(R.id.right_panel);
                fl.removeAllViews();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.right_panel, fragment);
                transaction.disallowAddToBackStack();
                transaction.commit();
                break;
            case R.layout.fragment_evaluating_work:
                EvaluatingWorkFragment evaluatingWorkFragment = EvaluatingWorkFragment.newInstance("", "");
                fl = (FrameLayout) findViewById(R.id.right_panel);
                fl.removeAllViews();
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.right_panel, evaluatingWorkFragment);
                transaction.disallowAddToBackStack();
                transaction.commit();
                break;
            case R.layout.fragment_work_steps_and_hazards:
                WorkStepsAndHazardsFragment workStepsAndHazardsFragment = WorkStepsAndHazardsFragment.newInstance("", "");
                fl = (FrameLayout) findViewById(R.id.right_panel);
                fl.removeAllViews();
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.right_panel, workStepsAndHazardsFragment);
                transaction.disallowAddToBackStack();
                transaction.commit();
                break;
            case R.layout.fragment_equipment_details:
                EquipmentDetailsFragment equipmentDetailsFragment = EquipmentDetailsFragment.newInstance("", "");
                fl = (FrameLayout) findViewById(R.id.right_panel);
                fl.removeAllViews();
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.right_panel, equipmentDetailsFragment);
                transaction.disallowAddToBackStack();
                transaction.commit();
                break;
            case R.layout.fragment_customer_sign_off:
                CustomerSignOffFragment customerSignOffFragment = CustomerSignOffFragment.newInstance("", "");
                fl = (FrameLayout) findViewById(R.id.right_panel);
                fl.removeAllViews();
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.right_panel, customerSignOffFragment);
                transaction.disallowAddToBackStack();
                transaction.commit();
                break;
        }

    }

    // JobsFragment listener method
    @Override
    public void onListFragmentInteraction(Form item) {
        SELECTEDFORM = item;
        Log.e(TAG, "Form Fragment call");
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, FormFragment.newInstance("intelligentsurveymanagement", "test2"));
        //transaction.replace(R.id.main_frame, LeftFragment.newInstance(1));
//        transaction.addToBackStack(FormFragment.TAG);
        transaction.disallowAddToBackStack();
        transaction.commit();
    }

}
