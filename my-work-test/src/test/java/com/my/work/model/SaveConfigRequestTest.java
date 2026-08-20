package com.my.work.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link SaveConfigRequest} Bean Validation 校验测试（SEC-P1-3 回归）。
 *
 * <p>使用 hibernate-validator 的 {@link ParameterMessageInterpolator}，避免运行时依赖 jakarta.el 实现。
 */
class SaveConfigRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.byProvider(HibernateValidator.class)
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory()
                .getValidator();
    }

    @Test
    void validRequestPasses() {
        SaveConfigRequest request = new SaveConfigRequest();
        request.setCode(1);
        request.setMsg("hello");

        Set<ConstraintViolation<SaveConfigRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty(), "合法参数不应产生校验错误");
    }

    @Test
    void nullCodeIsRejected() {
        SaveConfigRequest request = new SaveConfigRequest();
        request.setMsg("hello");

        Set<ConstraintViolation<SaveConfigRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("code")));
    }

    @Test
    void negativeCodeIsRejected() {
        SaveConfigRequest request = new SaveConfigRequest();
        request.setCode(-1);
        request.setMsg("hello");

        Set<ConstraintViolation<SaveConfigRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("code")));
    }

    @Test
    void blankMsgIsRejected() {
        SaveConfigRequest request = new SaveConfigRequest();
        request.setCode(1);
        request.setMsg("   ");

        Set<ConstraintViolation<SaveConfigRequest>> violations = validator.validate(request);

        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("msg")));
    }
}
