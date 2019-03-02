package common.api.query;

import java.util.List;

public class ListResult<T> {

    private int count;
    private List<T> list;

    public ListResult(List<T> list) {
        this.count = list.size();
        this.list = list;
    }

    public ListResult(int count, List<T> list) {
        this.count = count;
        this.list = list;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
