package intelligentsurveymanagement.retrofit;

import java.util.List;

import intelligentsurveymanagement.entity.Form;
import intelligentsurveymanagement.entity.Job;
import intelligentsurveymanagement.entity.JobForm;
import intelligentsurveymanagement.entity.LoginForm;
import intelligentsurveymanagement.entity.ModelApiResponse;
import intelligentsurveymanagement.entity.SOAnswersResponse;
import intelligentsurveymanagement.entity.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Suman on 08-03-2018.
 */

public interface ItemsService {

    @GET("job")
    @Headers({"Accept: application/json"})
    Call<List<Job>> getJobs();

    @GET("user")
    @Headers({"Accept: application/json"})
    Call<List<User>> getUsers();

    @GET("form/{formId}")
    @Headers({"Accept: application/json"})
    Call<List<JobForm>> getForm(@Path("formId") long formId);

    @GET("job/findByUser")
    @Headers({"Accept: application/json"})
    Call<List<Job>> getJobsByUsername(@Query("username") String username);

    @POST("user/login")
    @Headers({"Accept: application/json"})
    Call<ModelApiResponse> login(@Body LoginForm loginForm);

}
