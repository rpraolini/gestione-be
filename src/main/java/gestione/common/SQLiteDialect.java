package gestione.common;



import org.hibernate.boot.model.TypeContributions;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.DatabaseVersion;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;
import org.hibernate.engine.jdbc.env.spi.NameQualifierSupport;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.SqlTypes;
import org.hibernate.type.descriptor.jdbc.spi.JdbcTypeRegistry;
import org.hibernate.type.descriptor.jdbc.*;

public class SQLiteDialect extends Dialect {

    public SQLiteDialect() {
        super(DatabaseVersion.make(3, 0));
        registerColumnTypes();
    }

    private void registerColumnTypes() {
        // Registrazioni opzionali con metodi personalizzati
        // Hibernate 6 usa JdbcTypeRegistry e SqlTypes, quindi non serve sovrascrivere getTypeName
    }

    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        return new IdentityColumnSupportImpl() {
            @Override
            public boolean supportsIdentityColumns() {
                return true;
            }

            @Override
            public String getIdentitySelectString(String table, String column, int type) {
                return "select last_insert_rowid()";
            }

            @Override
            public String getIdentityColumnString(int type) {
                return "integer";
            }
        };
    }

    @Override
    public void contributeTypes(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
        JdbcTypeRegistry jdbcTypeRegistry = typeContributions.getTypeConfiguration().getJdbcTypeRegistry();

        jdbcTypeRegistry.addDescriptor(SqlTypes.BOOLEAN, new IntegerJdbcType());
        jdbcTypeRegistry.addDescriptor(SqlTypes.INTEGER, new IntegerJdbcType());
        jdbcTypeRegistry.addDescriptor(SqlTypes.BIGINT, new BigIntJdbcType());
        jdbcTypeRegistry.addDescriptor(SqlTypes.DOUBLE, new DoubleJdbcType());
        jdbcTypeRegistry.addDescriptor(SqlTypes.VARCHAR, new VarcharJdbcType());
     // Usa il BlobJdbcType già registrato
        JdbcType blobType = jdbcTypeRegistry.getDescriptor(SqlTypes.BLOB);
        jdbcTypeRegistry.addDescriptor(SqlTypes.BLOB, blobType);

        // Usa il ClobJdbcType già registrato
        JdbcType clobType = jdbcTypeRegistry.getDescriptor(SqlTypes.CLOB);
        jdbcTypeRegistry.addDescriptor(SqlTypes.CLOB, clobType);
    }

    @Override
    public NameQualifierSupport getNameQualifierSupport() {
        return NameQualifierSupport.NONE;
    }

    @Override
    public boolean supportsTemporaryTables() {
        return true;
    }


    @Override
    public boolean supportsIfExistsBeforeTableName() {
        return true;
    }

    @Override
    public boolean supportsCascadeDelete() {
        return false;
    }

    @Override
    public boolean supportsLobValueChangePropagation() {
        return false;
    }

    @Override
    public boolean supportsUnionAll() {
        return true;
    }
}

