package intelligentsurveymanagement.fragments;


import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.suman.intelligentsurveymanagement.R;
import com.github.gcacace.signaturepad.views.SignaturePad;

import org.w3c.dom.Text;

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
    //private TextView txtSentDate;
    private TextView txtSentJobDescription;
    private TextView txtSentInspector;
    private TextView txtSentLocation;
    private TextView txtEvalOne;
    private TextView txtEvalTwo;
    private TextView txtEvalThree;
    private TextView txtEvalFour;
    private TextView txtEvalFive;
    private TextView txtEvalSix;
    private ImageView imgSentImage;
    private TextView txtScanCode;
    private TextView txtCustomerName;
    private TextView txtRbWellTrainedEngg;
    private TextView txtRbProfessionalStandard;
    private TextView txtRbWellSupervisedEngineer;
    private TextView txtRbCustomerSatisfied;
    private TextView txtSentComment;
    private ImageView imgSentCustomerSignature;

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
        //txtSentDate = (TextView)view.findViewById(R.id.sent_date);
        txtSentJobDescription = (TextView)view.findViewById(R.id.sent_description);
        txtSentInspector = (TextView)view.findViewById(R.id.sent_inspector);
        txtSentLocation = (TextView)view.findViewById(R.id.sent_address);
        txtEvalOne = (TextView)view.findViewById(R.id.sent_eval_one);
        txtEvalTwo = (TextView)view.findViewById(R.id.sent_eval_two);
        txtEvalThree = (TextView)view.findViewById(R.id.sent_eval_three);
        txtEvalFour = (TextView)view.findViewById(R.id.sent_eval_four);
        txtEvalFive = (TextView)view.findViewById(R.id.sent_eval_five);
        txtEvalSix = (TextView)view.findViewById(R.id.sent_eval_six);
        imgSentImage = (ImageView) view.findViewById(R.id.sent_image);
        txtScanCode = (TextView)view.findViewById(R.id.sent_code);
        txtCustomerName = (TextView)view.findViewById(R.id.sent_customer_name);
        txtRbWellTrainedEngg = (TextView) view.findViewById(R.id.sent_customer_rating_one);
        txtRbProfessionalStandard = (TextView) view.findViewById(R.id.sent_customer_rating_two);
        txtRbWellSupervisedEngineer = (TextView) view.findViewById(R.id.sent_customer_rating_three);
        txtRbCustomerSatisfied = (TextView) view.findViewById(R.id.sent_customer_rating_four);
        txtSentComment = (TextView) view.findViewById(R.id.sent_comment);
        imgSentCustomerSignature = (ImageView) view.findViewById(R.id.sent_customer_signature);
    }

    private void loadDBData() {
        txtSentJobId.setText(Integer.toString(DigitalFormActivity.SELECTEDFORM.getFormid()));
        txtSentClientName.setText(DigitalFormActivity.SELECTEDFORM.getClientName());
        txtSentJobTitle.setText(DigitalFormActivity.SELECTEDFORM.getProject());
        //txtSentDate.setText(DigitalFormActivity.SELECTEDFORM.getDateTime());
        txtSentJobDescription.setText(DigitalFormActivity.SELECTEDFORM.getJobDescription());
        txtSentInspector.setText(DigitalFormActivity.SELECTEDFORM.getInspector());
        txtSentLocation.setText(DigitalFormActivity.SELECTEDFORM.getJobLocation());
        txtEvalOne.setText(DigitalFormActivity.SELECTEDFORM.isWalked()+"");
        txtEvalTwo.setText(DigitalFormActivity.SELECTEDFORM.isLiveSystem()+"");
        txtEvalThree.setText(DigitalFormActivity.SELECTEDFORM.isTrained()+"");
        txtEvalFour.setText(DigitalFormActivity.SELECTEDFORM.isMsds()+"");
        txtEvalFive.setText(DigitalFormActivity.SELECTEDFORM.isAirMonitoring()+"");
        txtEvalSix.setText(DigitalFormActivity.SELECTEDFORM.isWorkPermits()+"");
        if (DigitalFormActivity.SELECTEDFORM.getImage() != null) {
            loadimage();
        }
        txtScanCode.setText(DigitalFormActivity.SELECTEDFORM.getScanContent());
        txtCustomerName.setText(DigitalFormActivity.SELECTEDFORM.getCustomerName());
        txtRbWellTrainedEngg.setText(Float.toString(DigitalFormActivity.SELECTEDFORM.getWellTrainedEngineer()));
        txtRbProfessionalStandard.setText(Float.toString(DigitalFormActivity.SELECTEDFORM.getProfessionalStandard()));
        txtRbWellSupervisedEngineer.setText(Float.toString(DigitalFormActivity.SELECTEDFORM.getWellSupervisedEngineer()));
        txtRbCustomerSatisfied.setText(Float.toString(DigitalFormActivity.SELECTEDFORM.getCustomerSatisfied()));
        txtSentComment.setText(DigitalFormActivity.SELECTEDFORM.getCustomerComment());
        if (DigitalFormActivity.SELECTEDFORM.getCustomerSignature() != null) {
            loadSignature();
        }
    }

    private void loadimage() {
        imgSentImage.setBackgroundColor(Color.TRANSPARENT);
        imgSentImage.setImageBitmap(BitmapFactory.decodeByteArray(DigitalFormActivity.SELECTEDFORM.getImage(), 0, DigitalFormActivity.SELECTEDFORM.getImage().length));
    }

    private void loadSignature() {
        imgSentCustomerSignature.setBackgroundColor(Color.TRANSPARENT);
        imgSentCustomerSignature.setImageBitmap(BitmapFactory.decodeByteArray(DigitalFormActivity.SELECTEDFORM.getCustomerSignature(), 0, DigitalFormActivity.SELECTEDFORM.getCustomerSignature().length));
    }

}
