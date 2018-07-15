package intelligentsurveymanagement.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.suman.intelligentsurveymanagement.R;

import intelligentsurveymanagement.activities.DigitalFormActivity;
import intelligentsurveymanagement.utils.DatabaseInitializer;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link EvaluatingWorkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EvaluatingWorkFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Tag
    public static final String TAG = "EvaluatingWorkFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btnWalkedYes;
    private Button btnWalkedNo;
    private Button btnLiveSystemYes;
    private Button btnLiveSystemNo;
    private Button btnTrainedYes;
    private Button btnTrainedNo;
    private Button btnMSDSYes;
    private Button btnMSDSNo;
    private Button btnAirMonitoringYes;
    private Button btnAirMonitoringNo;
    private Button btnWorkPermitsYes;
    private Button btnWorkPermitsNo;
    private Button btnSave;

//    private OnFragmentInteractionListener mListener;

    public EvaluatingWorkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EvaluatingWorkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EvaluatingWorkFragment newInstance(String param1, String param2) {
        EvaluatingWorkFragment fragment = new EvaluatingWorkFragment();
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

        View view = inflater.inflate(R.layout.fragment_evaluating_work, container, false);

        initializeViews(view);

        loadDBData();

        btnWalkedYes.setOnClickListener(this);
        btnWalkedNo.setOnClickListener(this);
        btnTrainedYes.setOnClickListener(this);
        btnTrainedNo.setOnClickListener(this);
        btnLiveSystemYes.setOnClickListener(this);
        btnLiveSystemNo.setOnClickListener(this);
        btnMSDSYes.setOnClickListener(this);
        btnMSDSNo.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    private void loadDBData() {

        if (!DigitalFormActivity.SELECTEDFORM.getFormStatus().equals(DigitalFormActivity.INBOX)) {
            if (DigitalFormActivity.SELECTEDFORM.isWalked()) {
                btnWalkedYes.setBackgroundColor(Color.GREEN);
            } else {
                btnWalkedNo.setBackgroundColor(Color.RED);
            }

            if (DigitalFormActivity.SELECTEDFORM.isLiveSystem()) {
                btnLiveSystemYes.setBackgroundColor(Color.GREEN);
            } else {
                btnLiveSystemNo.setBackgroundColor(Color.RED);
            }

            if (DigitalFormActivity.SELECTEDFORM.isTrained()) {
                btnTrainedYes.setBackgroundColor(Color.GREEN);
            } else {
                btnTrainedNo.setBackgroundColor(Color.RED);
            }

            if (DigitalFormActivity.SELECTEDFORM.isMsds()) {
                btnMSDSYes.setBackgroundColor(Color.GREEN);
            } else {
                btnMSDSNo.setBackgroundColor(Color.RED);
            }

            if (DigitalFormActivity.SELECTEDFORM.isAirMonitoring()) {
                btnAirMonitoringYes.setBackgroundColor(Color.GREEN);
            } else {
                btnAirMonitoringNo.setBackgroundColor(Color.RED);
            }

            if (DigitalFormActivity.SELECTEDFORM.isWorkPermits()) {
                btnWorkPermitsYes.setBackgroundColor(Color.GREEN);
            } else {
                btnWorkPermitsNo.setBackgroundColor(Color.RED);
            }
        }

    }

    private void initializeViews(View view) {
        btnWalkedYes = (Button) view.findViewById(R.id.btn_walked_yes);
        btnWalkedNo = (Button) view.findViewById(R.id.btn_walked_no);
        btnLiveSystemYes = (Button) view.findViewById(R.id.btn_live_system_yes);
        btnLiveSystemNo = (Button) view.findViewById(R.id.btn_live_system_no);
        btnTrainedYes = (Button) view.findViewById(R.id.btn_trained_yes);
        btnTrainedNo = (Button) view.findViewById(R.id.btn_trained_no);
        btnMSDSYes = (Button) view.findViewById(R.id.btn_msds_yes);
        btnMSDSNo = (Button) view.findViewById(R.id.btn_msds_no);
        btnAirMonitoringYes = (Button) view.findViewById(R.id.btn_air_monitoring_yes);
        btnAirMonitoringNo = (Button) view.findViewById(R.id.btn_air_monitoring_no);
        btnWorkPermitsYes = (Button) view.findViewById(R.id.btn_work_permits_yes);
        btnWorkPermitsNo = (Button) view.findViewById(R.id.btn_work_permits_no);
        btnSave = (Button) view.findViewById(R.id.btn_evaluating_work_save);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_walked_yes:
                btnWalkedYes.setBackgroundColor(Color.GREEN);
                btnWalkedNo.setBackgroundColor(Color.GRAY);
                DigitalFormActivity.SELECTEDFORM.setWalked(true);
                break;

            case R.id.btn_walked_no:
                btnWalkedYes.setBackgroundColor(Color.GRAY);
                btnWalkedNo.setBackgroundColor(Color.RED);
                DigitalFormActivity.SELECTEDFORM.setWalked(false);
                break;

            case R.id.btn_live_system_yes:
                btnLiveSystemYes.setBackgroundColor(Color.GREEN);
                btnLiveSystemNo.setBackgroundColor(Color.GRAY);
                DigitalFormActivity.SELECTEDFORM.setLiveSystem(true);
                break;

            case R.id.btn_live_system_no:
                btnLiveSystemYes.setBackgroundColor(Color.GRAY);
                btnLiveSystemNo.setBackgroundColor(Color.RED);
                DigitalFormActivity.SELECTEDFORM.setLiveSystem(false);
                break;

            case R.id.btn_trained_yes:
                btnTrainedYes.setBackgroundColor(Color.GREEN);
                btnTrainedNo.setBackgroundColor(Color.GRAY);
                DigitalFormActivity.SELECTEDFORM.setTrained(true);
                break;

            case R.id.btn_trained_no:
                btnTrainedNo.setBackgroundColor(Color.GRAY);
                btnTrainedYes.setBackgroundColor(Color.GREEN);
                DigitalFormActivity.SELECTEDFORM.setTrained(false);
                break;

            case R.id.btn_msds_yes:
                btnMSDSYes.setBackgroundColor(Color.GREEN);
                btnMSDSNo.setBackgroundColor(Color.GRAY);
                DigitalFormActivity.SELECTEDFORM.setMsds(true);
                break;

            case R.id.btn_msds_no:
                btnMSDSYes.setBackgroundColor(Color.GRAY);
                btnMSDSNo.setBackgroundColor(Color.RED);
                DigitalFormActivity.SELECTEDFORM.setMsds(false);
                break;

            case R.id.btn_air_monitoring_yes:
                btnAirMonitoringYes.setBackgroundColor(Color.GREEN);
                btnAirMonitoringNo.setBackgroundColor(Color.GRAY);
                DigitalFormActivity.SELECTEDFORM.setAirMonitoring(true);
                break;

            case R.id.btn_air_monitoring_no:
                btnAirMonitoringYes.setBackgroundColor(Color.GRAY);
                btnAirMonitoringNo.setBackgroundColor(Color.RED);
                DigitalFormActivity.SELECTEDFORM.setAirMonitoring(false);
                break;

            case R.id.btn_work_permits_yes:
                btnWorkPermitsYes.setBackgroundColor(Color.GREEN);
                btnWorkPermitsNo.setBackgroundColor(Color.GRAY);
                DigitalFormActivity.SELECTEDFORM.setWorkPermits(true);
                break;

            case R.id.btn_work_permits_no:
                btnWorkPermitsYes.setBackgroundColor(Color.GRAY);
                btnWorkPermitsNo.setBackgroundColor(Color.RED);
                DigitalFormActivity.SELECTEDFORM.setWorkPermits(false);
                break;

            case R.id.btn_evaluating_work_save:
                DigitalFormActivity.SELECTEDFORM.setFormStatus(DigitalFormActivity.DRAFT);
                DatabaseInitializer.updateJob(DigitalFormActivity.appDatabase, DigitalFormActivity.appExecutors, getActivity().getApplicationContext(), DigitalFormActivity.SELECTEDFORM);
                DigitalFormActivity.initializeLists(getActivity());
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
                break;
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
