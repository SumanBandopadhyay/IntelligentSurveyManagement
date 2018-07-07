package com.example.suman.intelligentsurveymanagement.activity.com.example.suman.intelligentsurveymanagement.dummy;

import com.example.suman.intelligentsurveymanagement.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class LeftPanelContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<Integer, DummyItem> ITEM_MAP = new HashMap<Integer, DummyItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
//        for (int i = 1; i <= COUNT; i++) {
//            addItem(createDummyItem(i));
//        }
        DummyItem dummyItem = new DummyItem(R.layout.fragment_site_information, "Site Information Detail");
        addItem(dummyItem);
        dummyItem = new DummyItem(R.layout.fragment_evaluating_work, "Evaluating your Work Area");
        addItem(dummyItem);
        dummyItem = new DummyItem(R.layout.fragment_work_steps_and_hazards, "Work Steps and Hazards");
        addItem(dummyItem);
        dummyItem = new DummyItem(R.layout.fragment_equipment_details, "Equipment Details");
        addItem(dummyItem);
        dummyItem = new DummyItem(R.layout.fragment_customer_sign_off, "Customer Sign Off");
        addItem(dummyItem);
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

//    private static DummyItem createDummyItem(int position) {
//        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
//    }

//    private static DummyItem createDummyItem() {
//        DummyItem dummyItem = new DummyItem(R.layout.fragment_site_information, "Site Information Detail");
//        return dummyItem;
//    }

//    private static String makeDetails(int position) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("Details about Item: ").append(position);
//        for (int i = 0; i < position; i++) {
//            builder.append("\nMore details information here.");
//        }
//        return builder.toString();
//    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final int id;
        public final String content;

        public DummyItem(int id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
