package intelligentsurveymanagement.fragments;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.suman.intelligentsurveymanagement.R;
import com.github.gcacace.signaturepad.views.SignaturePad;

import intelligentsurveymanagement.activities.DigitalFormActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SentJobDetailsFragment extends Fragment {


    // Tag
    public static final String TAG = "SentJobDetails";

    private TextView txtSentJobId;
    private TextView txtSentClientName;
    private TextView txtSentJobTitle;
    private TextView txtSentDate;
    private TextView txtSentJobDescription;
    private TextView txtSentInspector;
    private TextView txtSentLocation;

    public SentJobDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sent_job_details, container, false);

        initializeViews(view);
        loadDBData();

        return view;
    }

    private void initializeViews(View view) {
        txtSentJobId = (TextView) view.findViewById(R.id.sent_job_id);
        txtSentClientName = (TextView)view.findViewById(R.id.sent_client_name);
        txtSentJobTitle = (TextView)view.findViewById(R.id.sent_job_title);
        txtSentDate = (TextView)view.findViewById(R.id.sent_date);
        txtSentJobDescription = (TextView)view.findViewById(R.id.sent_description);
        txtSentInspector = (TextView)view.findViewById(R.id.sent_inspector);
        txtSentLocation = (TextView)view.findViewById(R.id.sent_address);
    }

    private void loadDBData() {
        //txtSentJobId.setText(DigitalFormActivity.SELECTEDFORM.getFormid());
        txtSentClientName.setText(DigitalFormActivity.SELECTEDFORM.getClientName());
        //txtSentJobTitle.setText(DigitalFormActivity.SELECTEDFORM.);
        txtSentDate.setText(DigitalFormActivity.SELECTEDFORM.getDateTime());
        txtSentJobDescription.setText(DigitalFormActivity.SELECTEDFORM.getJobDescription());
        txtSentInspector.setText(DigitalFormActivity.SELECTEDFORM.getInspector());
        txtSentLocation.setText(DigitalFormActivity.SELECTEDFORM.getJobLocation());

    }

}
