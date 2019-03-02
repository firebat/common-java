package common.api.query;

import com.google.common.base.Splitter;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class GenericQuery extends LinkedHashMap<String, Object> implements ListQuery {

    public static Splitter semi = Splitter.on(';').trimResults().omitEmptyStrings();
    public static Splitter colon = Splitter.on(':').trimResults().omitEmptyStrings();
    public static transient String __limit = "__limit";
    public static transient String __offset = "__offset";
    public static transient String __order_by = "__order_by";

    @Override
    public GenericQuery fill(String name, Object value) {
        super.put(name, value);
        return this;
    }

    @Override
    public Integer getLimit() {
        return (Integer) get(__limit);
    }

    @Override
    public Integer getOffset() {
        return (Integer) get(__offset);
    }

    @Override
    public List<OrderBy> getOrderBy() {
        List list = (List) get(__order_by);
        if (list == null) {
            put(__order_by, list = new LinkedList());
        }
        return list;
    }

    @Override
    public GenericQuery like(String key) {

        Object value = get(key);

        if (value instanceof CharSequence && !"".equals(value)) {
            put(key, "%" + value + "%");
        }

        return this;
    }

    public GenericQuery limit(int limit) {
        return fill(__limit, limit);
    }

    public GenericQuery offset(int offset) {
        return fill(__offset, offset);
    }

    public GenericQuery orderBy(String orderBy) {
        List<OrderBy> orders = getOrderBy();

        for (String order : semi.splitToList(orderBy)) {
            Iterator<String> i = colon.split(order).iterator();
            String name = i.next();
            OrderBy.SortType sort = i.hasNext() ? OrderBy.SortType.valueOf(i.next().toLowerCase()) : OrderBy.SortType.asc;
            orders.add(new OrderBy(name, sort));
        }

        return this;
    }
}
