package vn.demo.starter.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ResourceNotFoundException extends AbstractThrowableProblem {

    public ResourceNotFoundException() {
        super(null, "Not Found", Status.NOT_FOUND, "Resource Not Found!");
    }

}
