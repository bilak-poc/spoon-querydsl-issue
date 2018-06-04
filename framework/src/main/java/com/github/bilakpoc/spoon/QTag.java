package com.github.bilakpoc.spoon;

import static com.mysema.query.types.PathMetadataFactory.forVariable;

import java.sql.Types;

import javax.annotation.Generated;

import com.mysema.query.sql.ColumnMetadata;
import com.mysema.query.types.Path;
import com.mysema.query.types.PathMetadata;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.StringPath;

@Generated("com.mysema.query.sql.codegen.MetaDataSerializer")
public class QTag extends com.mysema.query.sql.RelationalPathBase<Tag> {

    private static final long serialVersionUID = 1520555115;

    public static final QTag tag = new QTag("TAG");

    public final StringPath tagcode = createString("tagcode");

    public final NumberPath<Long> tagid = createNumber("tagid", Long.class);

    public final com.mysema.query.sql.PrimaryKey<Tag> tagPk = createPrimaryKey(tagid);

    public QTag(String variable) {
        super(Tag.class, forVariable(variable), "SDK", "TAG");
        addMetadata();
    }

    public QTag(String variable, String schema, String table) {
        super(Tag.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QTag(Path<? extends Tag> path) {
        super(path.getType(), path.getMetadata(), "SDK", "TAG");
        addMetadata();
    }

    public QTag(PathMetadata<?> metadata) {
        super(Tag.class, metadata, "SDK", "TAG");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(tagcode, ColumnMetadata.named("TAGCODE").withIndex(2).ofType(Types.VARCHAR).withSize(100).notNull());
        addMetadata(tagid, ColumnMetadata.named("TAGID").withIndex(1).ofType(Types.DECIMAL).withSize(10).notNull());
    }

}
