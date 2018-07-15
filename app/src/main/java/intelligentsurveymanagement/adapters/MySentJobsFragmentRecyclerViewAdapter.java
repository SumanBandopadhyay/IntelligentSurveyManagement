package intelligentsurveymanagement.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.suman.intelligentsurveymanagement.R;
import intelligentsurveymanagement.fragments.SentJobsFragment.OnListFragmentInteractionListener;
import intelligentsurveymanagement.entity.Form;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Form} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MySentJobsFragmentRecyclerViewAdapter extends RecyclerView.Adapter<MySentJobsFragmentRecyclerViewAdapter.ViewHolder> {

    private final List<Form> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MySentJobsFragmentRecyclerViewAdapter(List<Form> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_sentjobsfragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(Integer.toString(mValues.get(position).getFormid()));
        holder.mContentView.setText(mValues.get(position).getFormStatus());

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
        public final TextView mIdView;
        public final TextView mContentView;
        public Form mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.sent_item_number);
            mContentView = (TextView) view.findViewById(R.id.sent_content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
