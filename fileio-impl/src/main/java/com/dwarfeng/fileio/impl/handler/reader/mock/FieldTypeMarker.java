package com.dwarfeng.fileio.impl.handler.reader.mock;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@interface FieldTypeMarker {

    String value();
}
