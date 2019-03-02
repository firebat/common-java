package common.api.query;

public interface ListQuery extends Query<ListQuery> {

    Integer getLimit();

    Integer getOffset();
}
