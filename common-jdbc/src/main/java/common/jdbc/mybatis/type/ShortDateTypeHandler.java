package common.jdbc.mybatis.type;

import common.util.ShortDate;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShortDateTypeHandler extends BaseTypeHandler<ShortDate> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ShortDate parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public ShortDate getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return new ShortDate(rs.getInt(columnName));
    }

    @Override
    public ShortDate getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return new ShortDate(rs.getInt(columnIndex));
    }

    @Override
    public ShortDate getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return new ShortDate(cs.getInt(columnIndex));
    }
}
