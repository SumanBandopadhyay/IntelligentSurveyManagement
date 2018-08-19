package intelligentsurveymanagement.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.SharedPreferences;
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
import intelligentsurveymanagement.entity.Job;
import intelligentsurveymanagement.entity.SOAnswersResponse;
import intelligentsurveymanagement.executor.AppExecutors;
import intelligentsurveymanagement.fragments.CustomerSignOffFragment;
import intelligentsurveymanagement.fragments.DraftJobsFragment;
import intelligentsurveymanagement.fragments.EquipmentDetailsFragment;
import intelligentsurveymanagement.fragments.EvaluatingWorkFragment;
import intelligentsurveymanagement.fragments.FormFragment;
import intelligentsurveymanagement.fragments.HomeFragment;
import intelligentsurveymanagement.fragments.InboxJobsFragment;
import intelligentsurveymanagement.fragments.LeftFragment;
import intelligentsurveymanagement.fragments.SentJobDetailsFragment;
import intelligentsurveymanagement.fragments.SentJobsFragment;
import intelligentsurveymanagement.fragments.SiteInformationFragment;
import intelligentsurveymanagement.fragments.VideoReferenceFragment;
import intelligentsurveymanagement.fragments.WorkStepsAndHazardsFragment;
import intelligentsurveymanagement.retrofit.ApiUtils;
import intelligentsurveymanagement.retrofit.ItemsService;
import intelligentsurveymanagement.utils.DatabaseInitializer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private ItemsService mService;

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

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        appExecutors = new AppExecutors();

        sharedPreferences = this.getPreferences(MODE_PRIVATE);
        firstTimeAppLaunch = sharedPreferences.getBoolean(getString(R.string.firstTimeAppLaunch), true);
        Log.e(TAG, firstTimeAppLaunch+"");

        initializeLists(this);

        mService = ApiUtils.getItemsService();
        populateJobFromNetwork(mService, appExecutors, this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Log.e(TAG, "HomeFragment called");
//        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
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

    private static void populateJobFromNetwork(ItemsService mService, AppExecutors executors, Context context) {
        executors.getNetworkIO().execute(() -> {
            mService.getJobs().enqueue(new Callback<List<Job>>() {
                @Override
                public void onResponse(Call<List<Job>> call, Response<List<Job>> response) {
                    if (response.isSuccessful()) {
                        for (int i = 0; i < response.body().size(); i++) {
                            Log.e(TAG, "Jobs : " + response.body().get(i).getJobTitle());
                        }
                    } else {
                        int statusCode = response.code();
                        Log.e(TAG, "Status Code : " + statusCode + "; Error : " + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<List<Job>> call, Throwable t) {
                    Log.e(TAG, "Error during API call" + call.toString() + t);
                }
            });
        });
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
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
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
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
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.right_panel, fragment);
                transaction.disallowAddToBackStack();
                transaction.commit();
                break;
            case R.layout.fragment_evaluating_work:
                EvaluatingWorkFragment evaluatingWorkFragment = EvaluatingWorkFragment.newInstance("", "");
                fl = (FrameLayout) findViewById(R.id.right_panel);
                fl.removeAllViews();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.right_panel, evaluatingWorkFragment);
                transaction.disallowAddToBackStack();
                transaction.commit();
                break;
            case R.layout.fragment_work_steps_and_hazards:
                WorkStepsAndHazardsFragment workStepsAndHazardsFragment = WorkStepsAndHazardsFragment.newInstance("", "");
                fl = (FrameLayout) findViewById(R.id.right_panel);
                fl.removeAllViews();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.right_panel, workStepsAndHazardsFragment);
                transaction.disallowAddToBackStack();
                transaction.commit();
                break;
            case R.layout.fragment_equipment_details:
                EquipmentDetailsFragment equipmentDetailsFragment = EquipmentDetailsFragment.newInstance("", "");
                fl = (FrameLayout) findViewById(R.id.right_panel);
                fl.removeAllViews();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.right_panel, equipmentDetailsFragment);
                transaction.disallowAddToBackStack();
                transaction.commit();
                break;
            case R.layout.fragment_customer_sign_off:
                CustomerSignOffFragment customerSignOffFragment = CustomerSignOffFragment.newInstance("", "");
                fl = (FrameLayout) findViewById(R.id.right_panel);
                fl.removeAllViews();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.right_panel, customerSignOffFragment);
                transaction.disallowAddToBackStack();
                transaction.commit();
                break;
            case R.layout.fragment_video_reference:
                VideoReferenceFragment videoReferenceFragment = VideoReferenceFragment.newInstance("", "");
                fl = (FrameLayout) findViewById(R.id.right_panel);
                fl.removeAllViews();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.right_panel, videoReferenceFragment);
                transaction.disallowAddToBackStack();
                transaction.commit();
                break;
        }

    }

    // JobsFragment listener method
    @Override
    public void onListFragmentInteraction(Form item) {
        switch (item.getFormStatus()) {
            case DigitalFormActivity.SENT:
                SELECTEDFORM = item;
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame, new SentJobDetailsFragment());
                transaction.addToBackStack(SentJobDetailsFragment.TAG);
//                transaction.disallowAddToBackStack();
                transaction.commit();
                break;
            default:
                SELECTEDFORM = item;
                Log.e(TAG, "Form Fragment call");
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_frame, FormFragment.newInstance("intelligentsurveymanagement", "test2"));
                //transaction.replace(R.id.main_frame, LeftFragment.newInstance(1));
                transaction.addToBackStack(FormFragment.TAG);
//                transaction.disallowAddToBackStack();
                transaction.commit();
                break;
        }

    }

}
