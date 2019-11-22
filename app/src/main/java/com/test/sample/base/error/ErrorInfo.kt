package com.test.sample.base.error

/**
 * Date 2019-07-03 16:39
 *
 * @author tangxin
 */
enum class ErrorInfo(var code: Int, var message: String) {
    UNKNOWN_ERROR(999, "未知错误");

    companion object {

        fun getByCode(code: Int): ErrorInfo {
            for (errorInfo in values()) {
                if (errorInfo.code == code) {
                    return errorInfo
                }
            }
            return UNKNOWN_ERROR
        }
    }
}
