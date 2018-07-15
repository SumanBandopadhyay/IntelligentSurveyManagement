package intelligentsurveymanagement.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suman.intelligentsurveymanagement.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import intelligentsurveymanagement.activities.DigitalFormActivity;
import intelligentsurveymanagement.utils.DatabaseInitializer;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link EquipmentDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EquipmentDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "EquipmentDetails";

    TextView txtScanFormat;
    TextView txtScanContent;
    Button btnScanQRCode;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;

    public EquipmentDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EquipmentDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EquipmentDetailsFragment newInstance(String param1, String param2) {
        EquipmentDetailsFragment fragment = new EquipmentDetailsFragment();
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
        Log.e(EquipmentDetailsFragment.TAG, "View created");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equipment_details, container, false);
        // Inflate the layout for this fragment

        txtScanContent = (TextView) view.findViewById(R.id.txt_scan_content);
        txtScanFormat = (TextView) view.findViewById(R.id.txt_scan_format);
        btnScanQRCode = (Button) view.findViewById(R.id.btn_scan_qr);

        txtScanContent.setText(DigitalFormActivity.SELECTEDFORM.getScanContent());
        txtScanFormat.setText(DigitalFormActivity.SELECTEDFORM.getScanFormat());

        btnScanQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanQR(view);
            }
        });

        return view;
    }

   /* public void scanQR(){
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
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get(WorkStepsAndHazardsFragment.DATA);
//            imageEditPad.setLayoutParams(new FrameLayout.LayoutParams(imageBitmap.getWidth(), imageBitmap.getHeight()));
//            imageEditPad.setSignatureBitmap(imageBitmap);
//            Toast.makeText(getContext(), "Width : " + imageBitmap.getWidth() + ", Height : " + imageBitmap.getHeight(), Toast.LENGTH_LONG).show();
            Drawable drawable = new BitmapDrawable(imageBitmap);
            imageEditPad.setBackground(drawable);
//            Bitmap mutableBitmap = imageBitmap.copy(Bitmap.Config.ARGB_8888, true);
//            imageEditPad.draw(new Canvas(mutableBitmap));
        } else if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
//            vidCapturedVid.setVideoURI(videoUri);
//            vidCapturedVid.start();
        }
    }*/

    public void scanQR(View view){
//        IntentIntegrator integrator = new IntentIntegrator(getActivity());
//        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
//        integrator.setPrompt("Scan a QR Code");
//        integrator.setResultDisplayDuration(0);
//        integrator.setCameraId(0);  // Use a specific camera of the device
        //IntentIntegrator.forSupportFragment(EquipmentDetailsFragment.this).setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES).initiateScan();
        IntentIntegrator.forSupportFragment(EquipmentDetailsFragment.this).setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES).initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        Log.e(EquipmentDetailsFragment.TAG, "On Activity Result call");
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        //txtScanFormat.setVisibility(View.VISIBLE);
        if (scanningResult != null) {
            //we have a result
            String content = scanningResult.getContents();
            String format = scanningResult.getFormatName();

            Toast.makeText(getContext(),"Content : "+content, Toast.LENGTH_LONG).show();
            txtScanFormat.setText(format);
            txtScanContent.setText(content);

            DigitalFormActivity.SELECTEDFORM.setScanContent(content);
            DigitalFormActivity.SELECTEDFORM.setScanFormat(format);
            DigitalFormActivity.SELECTEDFORM.setFormStatus(DigitalFormActivity.DRAFT);
            DatabaseInitializer.updateJob(DigitalFormActivity.appDatabase, DigitalFormActivity.appExecutors, getActivity().getApplicationContext(), DigitalFormActivity.SELECTEDFORM);
            DigitalFormActivity.initializeLists(getActivity());

        }else{
            Toast toast = Toast.makeText(getContext(),"No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
   /* public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

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
   /* public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
