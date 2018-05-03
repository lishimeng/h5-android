package com.thingple.library.h5.bridge;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * module配置<br/>
 * Created by lism on 2017/8/15.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BridgeConfig {

    /**
     * module名字
     */
    String moduleName();
}
