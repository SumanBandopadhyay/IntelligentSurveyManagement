package intelligentsurveymanagement.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import intelligentsurveymanagement.activities.DigitalFormActivity;
import intelligentsurveymanagement.entity.Form;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DraftJobsContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<Form> ITEMS = DigitalFormActivity.DRAFTFORMS;

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<Integer, Form> ITEM_MAP = new HashMap<Integer, Form>();

    private static final int COUNT = 25;

    static {
        addItem();
    }

    private static void addItem() {
        ITEM_MAP = new HashMap<Integer, Form>();
        for (Form form: DigitalFormActivity.DRAFTFORMS) {
            ITEM_MAP.put(form.getFormid(), form);
        }
    }

}
