package com.github.bilakpoc.spoon;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.mysema.query.sql.Column;

@Generated("com.mysema.query.codegen.BeanSerializer")
public class Tag {

    public Tag() {
    }

    public Tag(String tagcode, Long tagid) {
        this.tagcode = tagcode;
        this.tagid = tagid;
    }

    @Column("TAGCODE")
    @Size(max = 100)
    @NotNull
    private String tagcode;

    @Column("TAGID")
    @NotNull
    private Long tagid;

    public String getTagcode() {
        return tagcode;
    }

    public void setTagcode(String tagcode) {
        this.tagcode = tagcode;
    }

    public Long getTagid() {
        return tagid;
    }

    public void setTagid(Long tagid) {
        this.tagid = tagid;
    }

    public String toString() {
        return "tagcode = " + tagcode + ", tagid = " + tagid;
    }

}
