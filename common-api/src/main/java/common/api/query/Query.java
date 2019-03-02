package common.api.query;

import java.util.List;
import java.util.Map;

public interface Query<T extends Query<T>> extends Map<String, Object> {

    T fill(String key, Object value);

    T like(String key);

    List<OrderBy> getOrderBy();
}
