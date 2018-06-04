package com.github.bilakpoc.spoon;

import javax.validation.Validator;

import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import com.mysema.query.sql.RelationalPath;
import com.mysema.query.types.PathMetadataFactory;
import com.mysema.query.types.path.StringPath;

public abstract class AbstractDSLDao {

    private Validator validator;

    protected StringPath rowid(RelationalPath<?> parent) {
          return new StringPath(PathMetadataFactory.forProperty(parent, "ROWID"));
    }

    public void setValidator(Validator validator) {
        // Unwrap to the native Validator with forExecutables support
        if (validator instanceof LocalValidatorFactoryBean) {
            this.validator = ((LocalValidatorFactoryBean) validator).getValidator();
        }
        else if (validator instanceof SpringValidatorAdapter) {
            this.validator = validator.unwrap(Validator.class);
        }
        else {
            this.validator = validator;
        }
    }

}
