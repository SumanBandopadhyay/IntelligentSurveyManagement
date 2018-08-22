package intelligentsurveymanagement.retrofit;

/**
 * Created by Suman on 08-03-2018.
 */

public class ApiUtils {

    public static final String BASE_URL = "http://35.154.85.196:8080/FieldSurveyManagement/";

    public static ItemsService getItemsService() {
        return RetrofitClient.getClient(BASE_URL).create(ItemsService.class);
    }

}
