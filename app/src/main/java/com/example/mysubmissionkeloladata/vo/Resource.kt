package com.example.mysubmissionkeloladata.vo

import androidx.annotation.NonNull
import androidx.annotation.Nullable

class Resource<T>(
    @NonNull val status: Status,
    @Nullable val data: T?,
    @Nullable val message: String?
) {
    companion object {
        fun <T> success(data: T?) = Resource(Status.SUCCESS, data, null)
        fun <T> error(msg: String?, data: T?) = Resource(Status.ERROR, data, msg)
        fun <T> loading(data: T?) = Resource(Status.LOADING, data, null)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }

        val resource = other as Resource<*>?

        if (resource != null) {
            if (status !== resource.status) {
                return false
            }
            if (if (message != null) message != (resource.message) else resource.message != null) {
                return false
            }
        }
        return data?.equals(resource?.data) ?: (resource?.data == null)
    }

    override fun hashCode(): Int {
        var result = status.hashCode()
        result = 31 * result + (message?.hashCode() ?: 0)
        result = 31 * result + (data?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Resource{status=$status, message='$message', data=$data}"
    }
}