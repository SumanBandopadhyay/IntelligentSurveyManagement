package intelligentsurveymanagement.dummy;

import intelligentsurveymanagement.activities.DigitalFormActivity;
import intelligentsurveymanagement.entity.Form;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class InboxJobsContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<Form> ITEMS = DigitalFormActivity.INBOXFORMS;

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<Integer, Form> ITEM_MAP = new HashMap<Integer, Form>();


    static {
        // Add some sample items.
        addItem();
    }

    private static void addItem() {
        ITEM_MAP = new HashMap<Integer, Form>();
        for (Form form: DigitalFormActivity.INBOXFORMS) {
            ITEM_MAP.put(form.getFormid(), form);
        }
    }

}
