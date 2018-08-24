package intelligentsurveymanagement.retrofit;

import java.util.List;
import java.util.Map;

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
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Suman on 08-03-2018.
 */

public interface ItemsService {

    @GET("job")
    Call<List<Job>> getJobs(
            @HeaderMap Map<String, String> headers
    );

    @GET("user")
    Call<List<User>> getUsers(
            @HeaderMap Map<String, String> headers
    );

    @GET("form/{formId}")
    Call<List<JobForm>> getForm(
            @Path("formId") long formId,
            @HeaderMap Map<String, String> headers
    );

    @GET("job/findByUser")
    Call<List<Job>> getJobsByUsername(
            @Query("username") String username,
            @HeaderMap Map<String, String> headers
    );

    @POST("user/login")
    Call<ModelApiResponse> login(
            @Body LoginForm loginForm,
            @HeaderMap Map<String, String> headers
    );

}
