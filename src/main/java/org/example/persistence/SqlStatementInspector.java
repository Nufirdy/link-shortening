package org.example.persistence;

import org.hibernate.resource.jdbc.spi.StatementInspector;

public class SqlStatementInspector implements StatementInspector {

    @Override
    public String inspect(String sql) {
        String rankSqlQuery = "SELECT rank from (SELECT DENSE_RANK() OVER (ORDER BY count DESC) as rank, link_id FROM link_stats) as r WHERE r.link_id ";
        return sql.replaceAll("\\{rank_sql_query}", rankSqlQuery);
    }
}