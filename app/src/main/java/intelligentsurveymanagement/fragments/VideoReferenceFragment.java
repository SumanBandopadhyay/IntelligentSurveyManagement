package intelligentsurveymanagement.fragments;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.suman.intelligentsurveymanagement.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Random;

import intelligentsurveymanagement.activities.DigitalFormActivity;
import intelligentsurveymanagement.utils.DatabaseInitializer;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link VideoReferenceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoReferenceFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btnCaptureVid;
    private VideoView vidCapturedVid;

    static final int REQUEST_VIDEO_CAPTURE = 2;
    private static final int RESULT_OK = -1;

    private static final String TAG = "VideoReference";

//    private OnFragmentInteractionListener mListener;

    public VideoReferenceFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoReferenceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoReferenceFragment newInstance(String param1, String param2) {
        VideoReferenceFragment fragment = new VideoReferenceFragment();
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
        View view = inflater.inflate(R.layout.fragment_video_reference, container, false);

        btnCaptureVid = (Button) view.findViewById(R.id.btn_capture_vid);
        vidCapturedVid = (VideoView) view.findViewById(R.id.vid_captured_vid);

        //Toast.makeText(getActivity(), DigitalFormActivity.SELECTEDFORM.getVideoURI(), Toast.LENGTH_LONG).show();

        vidCapturedVid.setZOrderOnTop(true);
        MediaController mediaController = new MediaController(getActivity());
        mediaController.setAnchorView(vidCapturedVid);
        mediaController.setMediaPlayer(vidCapturedVid);
        vidCapturedVid.setMediaController(mediaController);
        vidCapturedVid.setBackgroundColor(Color.TRANSPARENT);
        vidCapturedVid.requestFocus();

        if (DigitalFormActivity.SELECTEDFORM.getVideoURI() != null) {
            vidCapturedVid.setVideoURI(Uri.parse(DigitalFormActivity.SELECTEDFORM.getVideoURI()));
            vidCapturedVid.start();
        }

        btnCaptureVid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakeVideoIntent();
            }
        });

        return view;
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };

        //This method was deprecated in API level 11
        //Cursor cursor = managedQuery(contentUri, proj, null, null, null);

        CursorLoader cursorLoader = new CursorLoader(
                getActivity(),
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            //path converted from Uri
            String convertedPath = getRealPathFromURI(videoUri);
            Log.e(TAG, "Converted Path : " + convertedPath);
            ContentResolver cr = getActivity().getContentResolver();
            String mime = cr.getType(videoUri);
            Log.e(TAG, "Mime : " + mime);
            File file = new File(convertedPath);
            Log.e(TAG, "File : " + file.toString());
            vidCapturedVid.setVideoURI(videoUri);
            vidCapturedVid.start();
            DigitalFormActivity.SELECTEDFORM.setVideoURI(convertedPath);
            DigitalFormActivity.SELECTEDFORM.setFormStatus(DigitalFormActivity.DRAFT);
            DatabaseInitializer.updateJob(DigitalFormActivity.appDatabase, DigitalFormActivity.appExecutors, getActivity().getApplicationContext(), DigitalFormActivity.SELECTEDFORM);
            DigitalFormActivity.initializeLists(getActivity());
        }
    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

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

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
