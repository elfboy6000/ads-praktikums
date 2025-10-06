package ch.zhaw.ads;

/**
 * MySortedList -- ADS
 *
 * @author K. Rege 1.8.2021
 */
public class MySortedList extends MyList {
    @Override
    @SuppressWarnings("unchecked")
    public boolean add(Object val) {
        ListNode p = head.next;
        while (p != head && ((Comparable<Object>) p.value).compareTo(val) < 0) {
            p = p.next;
        }

        insertBefore(val, p);
        return true;
    }
}