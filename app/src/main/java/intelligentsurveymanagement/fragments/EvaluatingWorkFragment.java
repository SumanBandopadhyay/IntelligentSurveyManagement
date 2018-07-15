package intelligentsurveymanagement.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.suman.intelligentsurveymanagement.R;

import intelligentsurveymanagement.activities.DigitalFormActivity;

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

        // Inflate the layout for this fragment
        return view;
    }

    private void loadDBData() {

        if (DigitalFormActivity.SELECTEDFORM.isWalked()) {
            btnWalkedYes.setSelected(true);
            btnWalkedYes.setBackgroundColor(Color.GREEN);
        } else {
            btnWalkedNo.setSelected(true);
            btnWalkedNo.setBackgroundColor(Color.RED);
        }

        if (DigitalFormActivity.SELECTEDFORM.isLiveSystem()) {
            btnLiveSystemYes.setSelected(true);
            btnLiveSystemYes.setBackgroundColor(Color.GREEN);
        } else {
            btnLiveSystemNo.setSelected(true);
            btnLiveSystemNo.setBackgroundColor(Color.RED);
        }

        if (DigitalFormActivity.SELECTEDFORM.isTrained()) {
            btnTrainedYes.setSelected(true);
            btnTrainedYes.setBackgroundColor(Color.GREEN);
        } else {
            btnTrainedNo.setSelected(true);
            btnTrainedNo.setBackgroundColor(Color.RED);
        }

    }

    private void initializeViews(View view) {
        btnWalkedYes = (Button) view.findViewById(R.id.btn_walked_yes);
        btnWalkedNo = (Button) view.findViewById(R.id.btn_walked_no);
        btnLiveSystemYes = (Button) view.findViewById(R.id.btn_live_system_yes);
        btnLiveSystemNo = (Button) view.findViewById(R.id.btn_live_system_no);
        btnTrainedYes = (Button) view.findViewById(R.id.btn_trained_yes);
        btnTrainedNo = (Button) view.findViewById(R.id.btn_trained_no);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_walked_yes:
                btnWalkedYes.setSelected(true);
                btnWalkedYes.setBackgroundColor(Color.GREEN);
                break;

            case R.id.btn_walked_no:
                btnWalkedNo.setSelected(true);
                btnWalkedNo.setBackgroundColor(Color.GREEN);
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
