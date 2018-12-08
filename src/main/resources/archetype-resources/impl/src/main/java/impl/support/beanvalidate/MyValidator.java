package com.github.chenjianjx.srb4jfullsample.impl.support.beanvalidate;

/*
 * JBoss, Home of Professional Open Source
 * Copyright 2009, Red Hat, Inc. and/or its affiliates, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;
import java.net.IDN;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * @author chenjianjx@gmail.com . A lot of code is copied from
 *         hibernate-validator
 */
@Component
public class MyValidator {

    @Resource
    private Validator validator;

    private static String ATOM = "[a-z0-9!#$%&'*+/=?^_`{|}~-]";
    private static String DOMAIN = ATOM + "+(\\." + ATOM + "+)*";
    private static String IP_DOMAIN = "\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\]";

    /**
     * Regular expression for the local part of an email address (everything
     * before '@')
     */
    private final Pattern localEmailPattern = Pattern.compile(
            ATOM + "+(\\." + ATOM + "+)*", CASE_INSENSITIVE);

    /**
     * Regular expression for the domain part of an email address (everything
     * after '@')
     */
    private final Pattern emailDomainPattern = Pattern.compile(
            DOMAIN + "|" + IP_DOMAIN, CASE_INSENSITIVE);

    /**
     * validate the bean is not null; then validate its fields. Abort once there
     * is an error.
     *
     * @param bean
     * @return a single error message. If it's null, then it means there is no error
     */
    private <T> String validateBeanFastFail(T bean, String errIfBeanNull) {

        if (bean == null) {
            return errIfBeanNull;
        }
        Set<ConstraintViolation<T>> violations = validator.validate(bean);
        for (ConstraintViolation<T> violation : violations) {
            return violation.getMessage();
        }
        return null;
    }

    /**
     * validate the bean is not null; then validate its fields
     *
     * @param bean
     * @return the error object, will not return null.
     */
    public <T> ValidationError validateBean(T bean, String errIfBeanNull) {
        ValidationError result = new ValidationError();

        if (bean == null) {
            result.setNonFieldError(errIfBeanNull);
            return result;
        }

        Set<ConstraintViolation<T>> violations = validator.validate(bean);
        for (ConstraintViolation<T> violation : violations) {
            result.addFieldError(path2String(violation.getPropertyPath()), violation.getMessage());
        }
        return result;
    }

    private String path2String(Path propertyPath) {
        if (propertyPath == null) {
            return "Unknown";
        }
        return propertyPath.toString();
    }

    public boolean isEmailValid(CharSequence value) {
        if (value == null || value.length() == 0) {
            return true;
        }

        // split email at '@' and consider local and domain part separately;
        // note a split limit of 3 is used as it causes all characters following
        // to an (illegal) second @ character to
        // be put into a separate array element, avoiding the regex application
        // in this case since the resulting array
        // has more than 2 elements
        String[] emailParts = value.toString().split("@", 3);
        if (emailParts.length != 2) {
            return false;
        }

        // if we have a trailing dot in local or domain part we have an invalid
        // email address.
        // the regular expression match would take care of this, but IDN.toASCII
        // drops trailing the trailing '.'
        // (imo a bug in the implementation)
        if (emailParts[0].endsWith(".") || emailParts[1].endsWith(".")) {
            return false;
        }

        if (!matchPart(emailParts[0], localEmailPattern)) {
            return false;
        }

        return matchPart(emailParts[1], emailDomainPattern);
    }

    private boolean matchPart(String part, Pattern pattern) {
        try {
            part = IDN.toASCII(part);
        } catch (IllegalArgumentException e) {
            // occurs when the label is too long (>63, even though it should
            // probably be 64 - see
            // http://www.rfc-editor.org/errata_search.php?rfc=3696,
            // practically that should not be a problem)
            return false;
        }
        Matcher matcher = pattern.matcher(part);
        return matcher.matches();
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

}
