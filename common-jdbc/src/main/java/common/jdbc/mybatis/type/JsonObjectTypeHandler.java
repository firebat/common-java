package common.jdbc.mybatis.type;

import com.google.common.base.Strings;
import common.json.JsonMapper;
import common.json.MapperBuilder;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JsonObjectTypeHandler extends BaseTypeHandler<Object> {

    protected static final JsonMapper mapper = MapperBuilder.create().build();

    private final Class<?> clazz;

    public JsonObjectTypeHandler(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter == null ? null : mapper.writeValueAsString(parameter));
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return read(rs.getString(columnName), clazz);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return read(rs.getString(columnIndex), clazz);
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return read(cs.getString(columnIndex), clazz);
    }

    private Object read(String content, Class<?> clazz) {
        return Strings.isNullOrEmpty(content)
                ? null
                : mapper.readValue(content, clazz);
    }
}
