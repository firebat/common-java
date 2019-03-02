package common.api.query;

import junit.framework.TestCase;

public class GenericQueryTest extends TestCase {

    public void testOrderBy() {
        ListQuery query = new GenericQuery().fill("name", "alice").orderBy("age:desc").limit(10).offset(30);

        assertEquals("alice", query.get("name"));
        assertEquals(Integer.valueOf(10), query.getLimit());
        assertEquals(Integer.valueOf(30), query.getOffset());
        assertEquals(1, query.getOrderBy().size());
        assertEquals("age", query.getOrderBy().get(0).getName());
        assertTrue(OrderBy.SortType.desc == query.getOrderBy().get(0).getSort());
    }
}
