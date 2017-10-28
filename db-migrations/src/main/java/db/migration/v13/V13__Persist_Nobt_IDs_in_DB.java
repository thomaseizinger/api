package db.migration.v13;

import io.nobt.core.domain.NobtId;
import org.flywaydb.core.api.migration.MigrationChecksumProvider;
import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class V13__Persist_Nobt_IDs_in_DB implements SpringJdbcMigration, MigrationChecksumProvider {

    @Override
    public void migrate(JdbcTemplate jdbcTemplate) throws Exception {

        final List<Long> ids = jdbcTemplate.query("SELECT id from nobts", (rs, rowNum) -> rs.getLong("id"));

        jdbcTemplate.batchUpdate("UPDATE nobts SET externalId = ? WHERE id = ?", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {

                final Long currentId = ids.get(i);
                final NobtId nobtId = new NobtId(currentId);

                ps.setString(1, nobtId.toExternalIdentifier());
                ps.setLong(2, currentId);
            }

            @Override
            public int getBatchSize() {
                return ids.size();
            }
        });
    }

    @Override
    public Integer getChecksum() {
        return V13__Persist_Nobt_IDs_in_DB.class.getName().hashCode();
    }
}
