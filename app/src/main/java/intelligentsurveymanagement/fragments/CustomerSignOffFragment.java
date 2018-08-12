package intelligentsurveymanagement.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.suman.intelligentsurveymanagement.R;
import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.ByteArrayOutputStream;

import intelligentsurveymanagement.activities.DigitalFormActivity;
import intelligentsurveymanagement.utils.DatabaseInitializer;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CustomerSignOffFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerSignOffFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;

    private EditText edtCustomerName;
    private SignaturePad signaturePad;
    private RatingBar rbWellTrainedEngg;
    private RatingBar rbProfessionalStandard;
    private RatingBar rbWellSupervisedEngineer;
    private RatingBar rbCustomerSatisfied;
    private EditText edtCustomerComment;
    private Button btnSubmitData;
    //private Button btnSave;
    private Button btnClear;

    public CustomerSignOffFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomerSignOffFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomerSignOffFragment newInstance(String param1, String param2) {
        CustomerSignOffFragment fragment = new CustomerSignOffFragment();
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
        View view = inflater.inflate(R.layout.fragment_customer_sign_off, container, false);

        initializeViews(view);

        loadDBData();

        //disable both buttons at start
        btnSubmitData.setEnabled(false);
        btnClear.setEnabled(false);

        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
                btnSubmitData.setEnabled(true);
                btnClear.setEnabled(true);
            }

            @Override
            public void onClear() {
                btnSubmitData.setEnabled(false);
                btnClear.setEnabled(false);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signaturePad.clear();
            }
        });

        btnSubmitData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirm Submit !");
                builder.setMessage("Are you sure you want to submit the data ?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Data submitted successfully !", Toast.LENGTH_SHORT).show();

                        DigitalFormActivity.SELECTEDFORM.setCustomerName(edtCustomerName.getText().toString());
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        Bitmap bitmap = signaturePad.getSignatureBitmap();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] bytes = byteArrayOutputStream.toByteArray();
                        DigitalFormActivity.SELECTEDFORM.setCustomerSignature(bytes);
                        DigitalFormActivity.SELECTEDFORM.setWellTrainedEngineer(rbWellTrainedEngg.getRating());
                        DigitalFormActivity.SELECTEDFORM.setWellSupervisedEngineer(rbWellSupervisedEngineer.getRating());
                        DigitalFormActivity.SELECTEDFORM.setProfessionalStandard(rbProfessionalStandard.getRating());
                        DigitalFormActivity.SELECTEDFORM.setCustomerSatisfied(rbCustomerSatisfied.getRating());
                        DigitalFormActivity.SELECTEDFORM.setCustomerComment(edtCustomerComment.getText().toString());

                        if (DigitalFormActivity.SELECTEDFORM.getFormStatus().equals(DigitalFormActivity.INBOX) || DigitalFormActivity.SELECTEDFORM.getFormStatus().equals(DigitalFormActivity.DRAFT)) {
                            DigitalFormActivity.SELECTEDFORM.setFormStatus(DigitalFormActivity.SENT);
                        }

                        DatabaseInitializer.updateJob(DigitalFormActivity.appDatabase, DigitalFormActivity.appExecutors, getActivity().getApplicationContext(), DigitalFormActivity.SELECTEDFORM);
                        DigitalFormActivity.initializeLists(getActivity());

                        HomeFragment videoReferenceFragment = HomeFragment.newInstance("", "");
//                        FrameLayout fl = (FrameLayout) view.findViewById(R.id.right_panel);
//                        fl.removeAllViews();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.main_frame, videoReferenceFragment);
                        transaction.disallowAddToBackStack();
                        transaction.commit();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Submission Cancelled !", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();

                //Toast.makeText(getActivity(),"Data submitted successfully !",Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    private void loadDBData() {
        edtCustomerName.setText(DigitalFormActivity.SELECTEDFORM.getCustomerName());
        if (DigitalFormActivity.SELECTEDFORM.getCustomerSignature() != null) {
            signaturePad.setSignatureBitmap(BitmapFactory.decodeByteArray(DigitalFormActivity.SELECTEDFORM.getCustomerSignature(), 0, DigitalFormActivity.SELECTEDFORM.getCustomerSignature().length));
        }
        rbWellTrainedEngg.setRating(DigitalFormActivity.SELECTEDFORM.getWellTrainedEngineer());
        rbWellSupervisedEngineer.setRating(DigitalFormActivity.SELECTEDFORM.getWellSupervisedEngineer());
        rbProfessionalStandard.setRating(DigitalFormActivity.SELECTEDFORM.getProfessionalStandard());
        rbCustomerSatisfied.setRating(DigitalFormActivity.SELECTEDFORM.getCustomerSatisfied());
        edtCustomerComment.setText(DigitalFormActivity.SELECTEDFORM.getCustomerComment());
    }

    private void initializeViews(View view) {
        edtCustomerName = (EditText) view.findViewById(R.id.edt_customer_name);
        signaturePad = (SignaturePad) view.findViewById(R.id.signature_pad);
        rbWellTrainedEngg = (RatingBar) view.findViewById(R.id.rb_well_trained_engineer);
        rbWellSupervisedEngineer = (RatingBar) view.findViewById(R.id.rb_well_supervised_engineer);
        rbProfessionalStandard = (RatingBar) view.findViewById(R.id.rb_professional_standard);
        rbCustomerSatisfied = (RatingBar) view.findViewById(R.id.rb_customer_satisfied);
        edtCustomerComment = (EditText) view.findViewById(R.id.edt_customer_comment);
        btnSubmitData = (Button) view.findViewById(R.id.btn_submit);
        btnClear = (Button) view.findViewById(R.id.btn_clear_button);
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
