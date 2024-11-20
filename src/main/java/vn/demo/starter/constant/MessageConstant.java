package vn.demo.starter.constant;

public final class MessageConstant {

    private MessageConstant() {}

    public static final String INVALID_CREDENTIAL_ERR = "error.validate.login.invalid-credential";
    public static final String INVALID_PASSWORD_ERR = "error.validate.change-password.invalid-password";
    public static final String WRONG_PASSWORD_ERR = "error.validate.password.invalid-password";
    public static final String INVALID_EMAIL_ERR = "error.validate.reset-password.invalid-email";
    public static final String SAME_PASSWORD_ERR = "error.validate.change-password.same-password";
    public static final String INVALID_CREDENTIAL_AND_BLOCKED_ERR = "error.validate.login.invalid-email-password-and-account-was-blocked";
    public static final String INVALID_REFRESH_TOKEN_ERR = "error.validate.refresh-token.invalid";

    public static final String USER_ALREADY_EXIST_ERR = "error.validate.user.already-exist";
    public static final String USER_WAS_BLOCKED_ERR = "error.validate.user.blocked";
    public static final String USER_NOT_EXIST = "error.user.not-exist";
    public static final String USER_WAS_INACTIVE_ERR = "error.validate.user.inactive";
    public static final String ACTIVE_TOKEN_INVALID_ERR = "error.validate.register-token.invalid";
    public static final String ACTIVE_TOKEN_EXPIRED_ERR = "error.validate.register-token.expired";

    public static final String EMAIL_REQUIRED_ERROR = "error.validate.email.required";
    public static final String EMAIL_FORMAT_ERROR = "error.validate.email.format";
    public static final String EMAIL_MIN_LENGTH_ERROR = "error.validate.email.length.min";
    public static final String EMAIL_MAX_LENGTH_ERROR = "error.validate.email.length.max";

    public static final String PASSWORD_REQUIRED_ERROR = "error.validate.password.required";
    public static final String PASSWORD_MIN_LENGTH_ERROR = "error.validate.password.length.min";
    public static final String PASSWORD_MAX_LENGTH_ERROR = "error.validate.password.length.max";

    public static final String EXTERNAL_ORDER_ID_REQUIRED_ERROR = "error.validate.external-order-id.required";

    public static final String FIRST_NAME_REQUIRED_ERROR = "error.validate.first-name.required";
    public static final String FIRST_NAME_MAX_LENGTH_ERROR = "error.validate.first-name.length.max";
    public static final String LAST_NAME_REQUIRED_ERROR = "error.validate.last-name.required";
    public static final String LAST_NAME_MAX_LENGTH_ERROR = "error.validate.last-name.length.max";


}