package intelligentsurveymanagement.fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suman.intelligentsurveymanagement.R;
import intelligentsurveymanagement.activities.DigitalFormActivity;
import intelligentsurveymanagement.utils.DatabaseInitializer;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class SiteInformationFragment extends Fragment implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private double latitude;
    private double longitude;

    // TAG
    public static final String TAG = "SiteInformation";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private OnFragmentInteractionListener mListener;

    private MapView mapView;
    private GoogleMap googleMap;
    private TextView txtAddress;
    private TextView txtFormId;
    private EditText edtDateTime;
    private ImageButton imgDatePicker;
    private EditText edtInspector;
    private TextView txtClientName;
    private TextView txtJob;
    private EditText edtDescription;
    private Button btnSave;

    private int mYear, mMonth, mDay;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private FusedLocationProviderClient mFusedLocationClient;


    public SiteInformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SiteInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SiteInformationFragment newInstance(String param1, String param2) {
        SiteInformationFragment fragment = new SiteInformationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...
                }
            }

            ;
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the

                    // contacts-related task you need to do.

                    Toast.makeText(getActivity(), "Location permission granted", Toast.LENGTH_LONG).show();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    Toast.makeText(getActivity(), "You need to grant permission", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_site_information, container, false);

        initializeViews(view);

        loadDBData();

        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        MapsInitializer.initialize(getActivity().getApplicationContext());

        btnSave.setOnClickListener(this);
        imgDatePicker.setOnClickListener(this);

        return view;
    }

    private void loadDBData() {
        Toast.makeText(getActivity(), "DB Data Loaded", Toast.LENGTH_LONG).show();
        txtFormId.setText(Integer.toString(DigitalFormActivity.SELECTEDFORM.getFormid()));
        edtDateTime.setText(DigitalFormActivity.SELECTEDFORM.getDateTime());
        edtInspector.setText(DigitalFormActivity.SELECTEDFORM.getInspector());
        txtClientName.setText(DigitalFormActivity.SELECTEDFORM.getClientName());
        txtJob.setText(DigitalFormActivity.SELECTEDFORM.getProject());
        edtDescription.setText(DigitalFormActivity.SELECTEDFORM.getJobDescription());
        txtAddress.setText(DigitalFormActivity.SELECTEDFORM.getJobLocation());
    }

    private void initializeViews(View view) {
        txtAddress = (TextView) view.findViewById(R.id.txt_address);
        txtFormId = (TextView) view.findViewById(R.id.job_id);
        mapView = (MapView) view.findViewById(R.id.mapview);
        edtDateTime = (EditText) view.findViewById(R.id.edt_date_time);
        imgDatePicker = (ImageButton) view.findViewById(R.id.btn_date_picker);
        edtInspector = (EditText) view.findViewById(R.id.edt_inspector);
        txtClientName = (TextView) view.findViewById(R.id.txt_client_name);
        txtJob = (TextView) view.findViewById(R.id.txt_job);
        edtDescription = (EditText) view.findViewById(R.id.edt_description);
        btnSave = (Button) view.findViewById(R.id.btn_site_information_save);
    }

    @Override
    public void onResume() {
        super.onResume();
        //mapView.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        //mapView.onPause();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


    @Override
    public void onLocationChanged(Location location) {

//        String lat = location.getLatitude() + " ";
//        String lon = location.getLongitude() + " ";
        //txtLon.setText(lon);
        //txtLat.setText(lat);
        //Toast.makeText(getContext(), "Lat : " + location.getLatitude() + ", Long : " + location.getLongitude(), Toast.LENGTH_LONG).show();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        } else {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
            Task<Location> location = mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location == null) {
                                startLocationUpdates();
                            } else {
                                //If everything went fine lets get latitude and longitude
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();

                                //Toast.makeText(getContext(), latitude + " WORKS " + longitude + "", Toast.LENGTH_LONG).show();

                                mapView.getMapAsync(new OnMapReadyCallback() {
                                    @Override
                                    public void onMapReady(GoogleMap map) {
                                        googleMap = map;

                                        LatLng loc = new LatLng(latitude, longitude);
                                        googleMap.addMarker(new MarkerOptions().position(loc).title("Current Location"));

                                        CameraPosition cameraPosition = new CameraPosition.Builder().target(loc).zoom(12).build();
                                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                    }
                                });

                                Geocoder geocoder;
                                geocoder = new Geocoder(getActivity(), Locale.getDefault());

                                try {
                                    List<Address> addresses;
                                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                    String address = addresses.get(0).getAddressLine(0);
                                    txtAddress.setText(address);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

        }
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_site_information_save:
                DigitalFormActivity.SELECTEDFORM.setClientName(txtClientName.getText().toString());
                DigitalFormActivity.SELECTEDFORM.setDateTime(edtDateTime.getText().toString());
                DigitalFormActivity.SELECTEDFORM.setInspector(edtInspector.getText().toString());
                DigitalFormActivity.SELECTEDFORM.setProject(txtJob.getText().toString());
                DigitalFormActivity.SELECTEDFORM.setJobDescription(edtDescription.getText().toString());
                DigitalFormActivity.SELECTEDFORM.setFormStatus(DigitalFormActivity.DRAFT);
                DigitalFormActivity.SELECTEDFORM.setJobLocation(txtAddress.getText().toString());
                DatabaseInitializer.updateJob(DigitalFormActivity.appDatabase, DigitalFormActivity.appExecutors, getActivity().getApplicationContext(), DigitalFormActivity.SELECTEDFORM);
                DigitalFormActivity.initializeLists(getActivity());
                Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_date_picker:
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                edtDateTime.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
        }

    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
