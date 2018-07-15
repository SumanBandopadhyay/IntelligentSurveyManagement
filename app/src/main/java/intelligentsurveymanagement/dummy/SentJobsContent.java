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
public class SentJobsContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Form> ITEMS = DigitalFormActivity.SENTFORMS;

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<Integer, Form> ITEM_MAP = new HashMap<Integer, Form>();

    static {
        // Add some sample items.
//        for (int i = 1; i <= COUNT; i++) {
//            addItem(createDummyItem(i));
//        }
        addItem();
    }

    private static void addItem() {
//        ITEMS.add(item);
        for (Form form: ITEMS) {
            ITEM_MAP.put(form.getFormid(), form);
        }
    }

//    private static  createDummyItem(int position) {
//        return DigitalFormActivity.appDatabase.formDao().getSentForms();
//        //return new SentJobsItem(String.valueOf(position), "Item " + position, makeDetails(position));
//    }

//    private static String makeDetails(int position) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("Details about Item: ").append(position);
//        for (int i = 0; i < position; i++) {
//            builder.append("\nMore details information here.");
//        }
//        return builder.toString();
//    }
//
//    /**
//     * A dummy item representing a piece of content.
//     */
//    public static class SentJobsItem {
//        public final String id;
//        public final String content;
//        public final String details;
//
//        public SentJobsItem(String id, String content, String details) {
//            this.id = id;
//            this.content = content;
//            this.details = details;
//        }
//
//        @Override
//        public String toString() {
//            return content;
//        }
//    }
}
