package intelligentsurveymanagement.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.suman.intelligentsurveymanagement.R;
import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import intelligentsurveymanagement.activities.DigitalFormActivity;
import intelligentsurveymanagement.utils.DatabaseInitializer;
//import com.google.zxing.integration.android.IntentIntegrator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link WorkStepsAndHazardsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkStepsAndHazardsFragment extends Fragment {

    private static final String DATA = "data";
    private Button btnCaptureImage;
    private Button btnEditImage;
    //private Button btnSaveData;

//    private Button btnQRScan;
    private SignaturePad imageEditPad;
    private Button btnCaptureVid;
    private VideoView vidCapturedVid;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int RESULT_OK = -1;
    static final int REQUEST_VIDEO_CAPTURE = 2;
    private static  final int MY_PERMISSIONS_REQUEST_CAMERA = 3;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static final String TAG = "WorkStepsAndHazard";

    //private OnFragmentInteractionListener mListener;

    public WorkStepsAndHazardsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkStepsAndHazardsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkStepsAndHazardsFragment newInstance(String param1, String param2) {
        WorkStepsAndHazardsFragment fragment = new WorkStepsAndHazardsFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_work_steps_and_hazards, container, false);
        btnCaptureImage = (Button) view.findViewById(R.id.btn_capture_image);
        btnEditImage = (Button) view.findViewById(R.id.btn_edit_image);
        //btnSaveData = (Button) view.findViewById(R.id.btn_save_data);
        imageEditPad = (SignaturePad) view.findViewById(R.id.image_edit_pad);

        if (DigitalFormActivity.SELECTEDFORM.getImage() != null) {
            Drawable image = new BitmapDrawable(getResources(),BitmapFactory.decodeByteArray(DigitalFormActivity.SELECTEDFORM.getImage(), 0, DigitalFormActivity.SELECTEDFORM.getImage().length));
            imageEditPad.setBackground(image);
//            imageEditPad.setSignatureBitmap(BitmapFactory.decodeByteArray(DigitalFormActivity.SELECTEDFORM.getImage(), 0, DigitalFormActivity.SELECTEDFORM.getImage().length));
        }


        //btnCaptureVid = (Button) view.findViewById(R.id.btn_capture_video);
        //vidCapturedVid = (VideoView) view.findViewById(R.id.vid_captured_vid);

//        btnQRScan = (Button) view.findViewById(R.id.btn_qr_scan);
        imageEditPad.setEnabled(false);
        imageEditPad.setPenColor(Color.RED);

        /*vidCapturedVid.setZOrderOnTop(true);
        MediaController mediaController = new MediaController(getContext());
        mediaController.setAnchorView(vidCapturedVid);
        mediaController.setMediaPlayer(vidCapturedVid);
        vidCapturedVid.setMediaController(mediaController);
        vidCapturedVid.setBackgroundColor(Color.TRANSPARENT);*/

        //vidCapturedVid.requestFocus();
        //vidCapturedVid.setZOrderMediaOverlay(false);
        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
                btnEditImage.setEnabled(true);
                //btnSaveData.setEnabled(true);
            }
        });
        btnEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageEditPad.setEnabled(true);
            }
        });
       /* btnCaptureVid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakeVideoIntent();
            }
        });*/
//        btnQRScan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                scanQR();
//            }
//        });
        return view;
    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void scanQR(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            // Permission has already been granted
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.e(WorkStepsAndHazardsFragment.TAG, "On Activity Result call");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get(WorkStepsAndHazardsFragment.DATA);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            //imageBitmap.recycle();

            DigitalFormActivity.SELECTEDFORM.setImage(bytes);
            DigitalFormActivity.SELECTEDFORM.setFormStatus(DigitalFormActivity.DRAFT);
            DatabaseInitializer.updateJob(DigitalFormActivity.appDatabase, DigitalFormActivity.appExecutors, getActivity().getApplicationContext(), DigitalFormActivity.SELECTEDFORM);
            DigitalFormActivity.initializeLists(getActivity());
//            imageEditPad.setLayoutParams(new FrameLayout.LayoutParams(imageBitmap.getWidth(), imageBitmap.getHeight()));
//            imageEditPad.setSignatureBitmap(imageBitmap);
//            Toast.makeText(getContext(), "Width : " + imageBitmap.getWidth() + ", Height : " + imageBitmap.getHeight(), Toast.LENGTH_LONG).show();
            Drawable drawable = new BitmapDrawable(getResources(),imageBitmap);
            imageEditPad.setBackground(drawable);
//            Bitmap mutableBitmap = imageBitmap.copy(Bitmap.Config.ARGB_8888, true);
//            imageEditPad.draw(new Canvas(mutableBitmap));
        } else if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            vidCapturedVid.setVideoURI(videoUri);
            vidCapturedVid.start();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //IntentIntegrator integrator = new IntentIntegrator(getActivity());
                    //integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                    //integrator.setPrompt("Scan a QR Code");
                    //integrator.setResultDisplayDuration(0);
                    //integrator.setCameraId(3);  // Use a specific camera of the device
                    //integrator.initiateScan();
                } else {
                    Toast.makeText(getContext(), "Camera permission denied. Cannot scan QR.", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
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
