package common.api.query;

public class OrderBy {

    private String name;
    private SortType sort;

    public OrderBy(String name, SortType sort) {
        this.name = name;
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SortType getSort() {
        return sort;
    }

    public void setSort(SortType sort) {
        this.sort = sort;
    }

    enum SortType {
        asc,
        desc
    }
}
