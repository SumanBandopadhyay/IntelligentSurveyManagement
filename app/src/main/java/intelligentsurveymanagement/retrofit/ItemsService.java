package intelligentsurveymanagement.retrofit;

import java.util.List;

import intelligentsurveymanagement.entity.Job;
import intelligentsurveymanagement.entity.SOAnswersResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
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
    Call<SOAnswersResponse> getUsers();

}
