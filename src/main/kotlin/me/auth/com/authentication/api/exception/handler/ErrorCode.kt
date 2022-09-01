package me.auth.com.authentication.api.exception.handler

enum class ErrorCode(val code: Int, val message: String) {
    SUCCESSFUL(0, ""),
    URL_NOT_FOUND(1, "URL not found"),
    METHOD_NOT_ALLOWED(2, "Method not allowed"),
    MISSING_REQUIRED_KEY(3, "Missing required field: %s"),
    MISSING_VALUE_OF_REQUIRED_KEY(4, "Missing value of required field: %s"),
    UNAUTHORIZED_ACCESS(5, "Unauthorized access, %s"),
    ID_NOT_FOUND(6, "%s is not found"),
    RECORD_ALREADY_EXISTED(7, "%s record already exists"),
    INVALID_INPUT_FORMAT(8, "Invalid format: %s"),
    MISSING_REQUIRED_FILTERING_PARAM(9, "Missing query params: %s"),
    INCORRECT_PASSWORD(10, "%s"),
    ENTRY_DATE_FROM_TOO_BIG(11, "Value of field: %s is bigger than value of field: %s"),
    ENTRY_DATE_FROM_MISSING(12, "Field: %s is empty"),
    INVALID_TOKEN(13, "Invalid Token"),
    TOKEN_EXPIRED(14, "Token Expired"),
    BANK_DONT_SET_SETTLEMENT_ACCOUNT(15, "Settlement account of Bank ID: %s hasn't set yet"),
    INVALID_SIGN_AND_CLOSED(16, "Invalid sign for transactions because %s."), // ##### when user try to sign and close CCY (USD & KHR) transactions together
    INVALID_SIGN_AND_CLOSED_SIGNATURE(17, "Invalid Signatures"),
    SENDER_AND_RECEIVER_BANK_EQUAL(18, "Sender account and Receiver account [%s] must not be the same"),
    PASSWORD_POLICY(19, "%s"), // ##### first time change password for FE - need to inform FE if code is changed
    LOGGED_IN_ATTEMPT(20, "Your Login attempt reaches maximum limitation: %s"),
    PASSWORD_COMPLEXITY(21, "Your password is not comply with password complexity setting: %s"),
    OLD_PASSWORD_SETTING(22, "Old password cannot be re-used in: %s"),
    INTERNAL_ERROR(23, "Internal Server Error"),
    NO_USER_AVAILABLE(24, "No User Available. %s"),
    STRING_EXCEEDING_LENGTH(25, "%s's length is over 255 chars."),
    INVALID_BANK_STATUS(26, "Bank %s is Inactive"),
    INVALID_CURRENCY_STATUS(27, "Currency is Inactive."),
    INVALID_SIGN_AND_SENT(28, "Invalid Content"), // ##### when user try to sign and send CCY (USD & KHR) batches together
    INVALID_SIGN_AND_SENT_SIGNATURE(29, "Invalid Signatures"),
    ISO_MESSAGE_CONTAINED_INVALID_FORMAT(30, "ISO contained invalid format."),
    ISO_MESSAGE_CONTAINED_INVALID_DATA(31, "ISO contained invalid data: %s."),
    DYNAMIC_MESSAGE(32, "%s"), // request (HTTP) 400 - For backend (concate error messages)
    CHANGE_PASSWORD(33, "%s"),
    INVALID_AMOUNT_WITH_CURRENCY(34, "invalid amount with currency: %s"),
    MUST_GREATER_OR_EQUAL(35, "%s must be greater than or equal to #ANY_VALUE"),
    UNRECOGNIZED_FIELD(36, "Unrecognized field: %s"),
    ACCOUNT_INQUIRY_INACTIVE(37, "%s"),
    SDR_FILE_ERROR(38, "Invalid SDR Content: %s"),
    FORCE_SETTLEMENT_ERROR(39, "Error Force Settlement NetFileID[%s]"),
    INVALID_SIGNATURE(40, "Invalid signature: %s"),
    ACCOUNT_INQUIRY_INVALID(41, "%s"),
    ACCOUNT_INQUIRY_ERR_CONNECT(42, "%s"),
    INVALID_UPDATE_TRANSACTION(43, "Transaction can't be updated because %s"),
    INVALID_SYSTEM_SETTING_STATUS(44, "Selected %s is not active."),
    INVALID_MIN_AND_MAX(45, "%s"),
    VALUE_NOT_IN_RANGE(46, "%s must be in range MIN[%s] and MAX[%s]."),
    UNSUPPORTED_MEDIA_TYPE(47, "Unsupported media type"),
    INVALID_INPUT_DATA_TYPE(48, "Invalid input data type in field: %s"),
    INVALID_STATUS_INPUT(49, "Invalid status input"),
    INVALID_REFUND(50, "%s"),
    INVALID_TRANSFER_AMOUNT_WITH_KHR(51, "TransferAmount[%s] is invalid for CurrencyCode[KHR]."),
    INVALID_PATH_PARAM(52, "Invalid path param %s value"),
    PARAM_NOT_FOUND(53, "%s is not found."),
    INACTIVE_STATUS_FIELD(54, "%s is not active."),
    INVALID_STATUS(55, "%s has invalid status."),
    NO_TRANSACTION_TO_SETTLE(56, "There are no any transactions for settle.");
}