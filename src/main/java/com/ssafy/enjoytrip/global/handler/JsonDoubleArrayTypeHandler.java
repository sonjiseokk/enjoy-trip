package com.ssafy.enjoytrip.global.handler;

import com.google.gson.Gson;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JsonDoubleArrayTypeHandler implements TypeHandler<double[]> {
    private static final Gson gson = new Gson();

    @Override
    public void setParameter(PreparedStatement ps, int i, double[] parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, gson.toJson(parameter));
    }

    @Override
    public double[] getResult(ResultSet rs, String columnName) throws SQLException {
        return parseJson(rs.getString(columnName));
    }

    @Override
    public double[] getResult(ResultSet rs, int columnIndex) throws SQLException {
        return parseJson(rs.getString(columnIndex));
    }

    @Override
    public double[] getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parseJson(cs.getString(columnIndex));
    }

    private double[] parseJson(String json) {
        if (json == null) return null;
        return gson.fromJson(json, double[].class);
    }
}

