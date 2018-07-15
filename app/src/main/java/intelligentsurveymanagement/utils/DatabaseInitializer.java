package intelligentsurveymanagement.utils;

import android.content.Context;
import android.util.Log;

import intelligentsurveymanagement.activities.DigitalFormActivity;
import intelligentsurveymanagement.database.AppDatabase;
import intelligentsurveymanagement.entity.Form;
import intelligentsurveymanagement.executor.AppExecutors;

import java.util.List;

import io.reactivex.annotations.NonNull;

public class DatabaseInitializer {

    private static final String TAG = "DatabaseInit";

    public static void populateAsync(@NonNull final AppDatabase db, AppExecutors executors, Context context) {
        executors.getDiskIO().execute(() -> {
            // Add delay to simulate long running Action
            // addDelay();
            // Generate the data for pre-population
            AppDatabase database = AppDatabase.getInstance(context.getApplicationContext());
            populateWithTestData(database);
        });
    }

    public static void getAllJobs(@NonNull final AppDatabase db, AppExecutors executors, Context context) {
        executors.getDiskIO().execute(() -> {
            // Add delay to simulate long running Action
            // addDelay();
            // Generate the data for pre-population
            AppDatabase database = AppDatabase.getInstance(context.getApplicationContext());
            DigitalFormActivity.ALLFORMS = database.formDao().getAllForms();
        });
    }

    public static void updateJob(@NonNull final AppDatabase db, AppExecutors executors, Context context, Form form) {
        executors.getDiskIO().execute(() -> {
            // Add delay to simulate long running Action
            // addDelay();
            // Generate the data for pre-population
            AppDatabase database = AppDatabase.getInstance(context.getApplicationContext());
            database.formDao().updateForm(form);
        });
    }

    public static void getSentJobs(@NonNull final AppDatabase db, AppExecutors executors, Context context) {
        executors.getDiskIO().execute(() -> {
            // Add delay to simulate long running Action
            // addDelay();
            // Generate the data for pre-population
            AppDatabase database = AppDatabase.getInstance(context.getApplicationContext());
            DigitalFormActivity.SENTFORMS = database.formDao().getSentForms();
            Log.e(TAG, "Get Sent Jobs" + DigitalFormActivity.SENTFORMS.size());
        });
    }

    public static void getInboxJobs(AppDatabase db, AppExecutors executors, Context context) {
        executors.getDiskIO().execute(() -> {
            // Add delay to simulate long running Action
            // addDelay();
            // Generate the data for pre-population
            AppDatabase database = AppDatabase.getInstance(context.getApplicationContext());
            DigitalFormActivity.INBOXFORMS = database.formDao().getInboxForms();
        });
    }

    private static void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void populateWithTestData(AppDatabase db) {
        Form form = new Form();
        form.setFormid(1);
        form.setClientName("Client 1");
        form.setFormStatus("sent");
        addForm(db, form);
        form = new Form();
        form.setFormid(2);
        form.setFormStatus("sent");
        addForm(db, form);
        form = new Form();
        form.setFormid(3);
        form.setFormStatus("sent");
        addForm(db, form);

        List<Form> forms = db.formDao().getAllForms();
        Log.e(DatabaseInitializer.TAG, "Rows Count : " + forms.size());
    }

    private static void addForm(AppDatabase db, final Form form) {
        db.runInTransaction(() -> {
            db.formDao().insertForm(form);
        });
    }
}
