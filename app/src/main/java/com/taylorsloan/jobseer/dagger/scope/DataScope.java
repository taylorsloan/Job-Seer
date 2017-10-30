package com.taylorsloan.jobseer.dagger.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by taylo on 10/29/2017.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface DataScope {
}
