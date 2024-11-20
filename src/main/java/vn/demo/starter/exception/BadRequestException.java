package vn.demo.starter.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import static vn.demo.starter.controller.advice.ExceptionTranslator.BAD_REQUEST_TITLE;

public class BadRequestException extends AbstractThrowableProblem {

    public BadRequestException(String message) {
        super(null, BAD_REQUEST_TITLE, Status.BAD_REQUEST, message);
    }
}
