package com.example.mysubmissionkeloladata.data.remote.response

import androidx.annotation.Nullable

class ApiResponse<T>(val status: StatusResponse, @Nullable val body: T, @Nullable val message: String?
) {
    companion object {
        fun <T> success(@Nullable body: T): ApiResponse<T> {
            return ApiResponse(
                StatusResponse.SUCCESS, body, null
            )
        }

        fun <T> empty(msg: String, body: T): ApiResponse<T> {
            return ApiResponse(
                StatusResponse.EMPTY, body, msg
            )
        }

        fun <T> error(msg: String, @Nullable body: T): ApiResponse<T> {
            return ApiResponse(
                StatusResponse.ERROR, body, msg
            )
        }
    }
}