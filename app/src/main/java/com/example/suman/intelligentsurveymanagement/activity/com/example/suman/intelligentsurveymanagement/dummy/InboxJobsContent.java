package com.example.suman.intelligentsurveymanagement.activity.com.example.suman.intelligentsurveymanagement.dummy;

import com.example.suman.intelligentsurveymanagement.activity.com.example.suman.intelligentsurveymanagement.activities.DigitalFormActivity;
import com.example.suman.intelligentsurveymanagement.activity.com.example.suman.intelligentsurveymanagement.entity.Form;

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
    public static final List<Form> ITEMS = DigitalFormActivity.INBOXFORMS;

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<Integer, Form> ITEM_MAP = new HashMap<Integer, Form>();


    static {
        // Add some sample items.
        addItem();
    }

    private static void addItem() {
        for (Form form: ITEMS) {
            ITEM_MAP.put(form.getFormid(), form);
        }
    }

}
