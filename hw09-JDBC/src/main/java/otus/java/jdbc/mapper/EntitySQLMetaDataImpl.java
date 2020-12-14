package otus.java.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final EntityClassMetaData<?> classMetadata;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> classMetadata) {
        this.classMetadata = classMetadata;
    }

    @Override
    public String getSelectAllSql() {
        StringBuilder sb = new StringBuilder("select ");
        sb.append(getFields(classMetadata.getAllFields()));
        sb.append(" from ");
        sb.append(classMetadata.getName().toLowerCase());
        return sb.toString();
    }

    private String getFields(List<Field> fields) {
        return fields.stream().map(Field::getName).map(String::toLowerCase)
                .collect(Collectors.joining(", "));
    }

    @Override
    public String getSelectByIdSql() {
        StringBuilder sb = new StringBuilder(getSelectAllSql());
        sb.append(" where ");
        sb.append(classMetadata.getIdField().getName().toLowerCase());
        sb.append(" = ?");
        return sb.toString();
    }

    @Override
    public String getInsertSql() {
        StringBuilder sb = new StringBuilder("insert into ");
        sb.append(classMetadata.getName().toLowerCase());
        sb.append(" (");
        sb.append(getFields(classMetadata.getFieldsWithoutId()));
        sb.append(") values(");
        appendParamsLine(sb, classMetadata.getFieldsWithoutId().size());
        sb.append(")");
        return sb.toString();
    }

    @Override
    public String getInsertUpdateSql() {
        StringBuilder sb = new StringBuilder("insert into ");
        sb.append(classMetadata.getName().toLowerCase());
        sb.append(" (");
        sb.append(getFields(classMetadata.getAllFields()));
        sb.append(") values(");
        appendParamsLine(sb, classMetadata.getAllFields().size());
        sb.append(") on conflict (");
        sb.append(classMetadata.getIdField().getName().toLowerCase());
        sb.append(") do update set ");
        appendExcludedParams(sb, classMetadata.getFieldsWithoutId());
        return sb.toString();
    }

    private void appendParamsLine(StringBuilder sb, int amount) {
        for (int i = 1; i <= amount; i++) {
            sb.append("?");
            if (i < amount) {
                sb.append(", ");
            }
        }
    }

    private void appendExcludedParams(StringBuilder sb, List<Field> fields) {
        for (Iterator<Field> it = fields.iterator(); it.hasNext(); ) {
            String fieldName = it.next().getName().toLowerCase();
            sb.append(fieldName);
            sb.append(" = excluded.");
            sb.append(fieldName);
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
    }
}
