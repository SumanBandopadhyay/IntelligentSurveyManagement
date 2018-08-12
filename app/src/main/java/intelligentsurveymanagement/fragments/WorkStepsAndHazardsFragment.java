package intelligentsurveymanagement.fragments;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    private ImageView imgImage1;
    private ImageView imgImage2;
    private ImageView imgImage3;
    private Button btnAddImg;

    private static int IMG_COUNT = 0;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int RESULT_OK = -1;
//    static final int REQUEST_VIDEO_CAPTURE = 2;
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

        imgImage1 = (ImageView) view.findViewById(R.id.img_image_1);
        imgImage2 = (ImageView) view.findViewById(R.id.img_image_2);
        imgImage3 = (ImageView) view.findViewById(R.id.img_image_3);
        btnAddImg = (Button) view.findViewById(R.id.btn_add_img);

//        if (DigitalFormActivity.SELECTEDFORM.getImage() != null) {
//            loadimage();
//        }

        btnAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (IMG_COUNT < 3) {
                    dispatchTakePictureIntent();
                    IMG_COUNT++;
                } else {
                    Toast.makeText(getActivity(), "Maximum number of Pics are already added", Toast.LENGTH_LONG).show();
                }
            }
        });

        imgImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInDialog(imgImage1.getDrawable());
            }
        });

        return view;
    }

    private void showInDialog(Drawable drawable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Image Saved", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setNeutralButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "Image Editted", Toast.LENGTH_SHORT).show();
            }
        });
        final AlertDialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.image_dialog_layout, null);
        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.show();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                ImageView image = (ImageView) dialog.findViewById(R.id.imgDialogImage);
                Bitmap icon = drawableToBitmap(drawable);
                image.setImageBitmap(icon);
                float imageWidthInPX = (float)image.getWidth();

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(imageWidthInPX),
                        Math.round(imageWidthInPX * (float)icon.getHeight() / (float)icon.getWidth()));
                image.setLayoutParams(layoutParams);


            }
        });
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

//    private void loadimage() {
//        imageEditPad.setBackgroundColor(Color.TRANSPARENT);
//        imageEditPad.setSignatureBitmap(BitmapFactory.decodeByteArray(DigitalFormActivity.SELECTEDFORM.getImage(), 0, DigitalFormActivity.SELECTEDFORM.getImage().length));
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.e(WorkStepsAndHazardsFragment.TAG, "On Activity Result call");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get(WorkStepsAndHazardsFragment.DATA);
            switch (IMG_COUNT) {
                case 1:
                    imgImage1.setVisibility(View.VISIBLE);
                    imgImage1.setImageBitmap(imageBitmap);
                    break;
                case 2:
                    imgImage2.setVisibility(View.VISIBLE);
                    imgImage2.setImageBitmap(imageBitmap);
                    break;
                case 3:
                    imgImage3.setVisibility(View.VISIBLE);
                    imgImage3.setImageBitmap(imageBitmap);
                    break;
            }

//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//            byte[] bytes = byteArrayOutputStream.toByteArray();
//            DigitalFormActivity.SELECTEDFORM.setImage(bytes);
//            DigitalFormActivity.SELECTEDFORM.setFormStatus(DigitalFormActivity.DRAFT);
//            DatabaseInitializer.updateJob(DigitalFormActivity.appDatabase, DigitalFormActivity.appExecutors, getContext(), DigitalFormActivity.SELECTEDFORM);
//            DigitalFormActivity.initializeLists(getActivity());
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
