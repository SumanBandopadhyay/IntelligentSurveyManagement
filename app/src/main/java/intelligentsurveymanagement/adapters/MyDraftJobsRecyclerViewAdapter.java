package intelligentsurveymanagement.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.suman.intelligentsurveymanagement.R;

import intelligentsurveymanagement.entity.Form;
import intelligentsurveymanagement.fragments.DraftJobsFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Form} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyDraftJobsRecyclerViewAdapter extends RecyclerView.Adapter<MyDraftJobsRecyclerViewAdapter.ViewHolder> {

    private final List<Form> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyDraftJobsRecyclerViewAdapter(List<Form> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_draftjobs, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.txtDraftJobId.setText(Integer.toString(mValues.get(position).getFormid()));
        holder.txtDraftJobTitle.setText(mValues.get(position).getProject());
        holder.txtDraftClientName.setText(mValues.get(position).getClientName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txtDraftJobId;
        public final TextView txtDraftJobTitle;
        public final TextView txtDraftClientName;
        public Form mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtDraftJobId = (TextView) view.findViewById(R.id.txt_draft_job_id);
            txtDraftJobTitle = (TextView) view.findViewById(R.id.txt_draft_job_title);
            txtDraftClientName = (TextView) view.findViewById(R.id.txt_draft_client_name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + txtDraftJobTitle.getText() + "'";
        }
    }
}
