package com.example.mysubmissionkeloladata.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.mysubmissionkeloladata.data.remote.response.ApiResponse
import com.example.mysubmissionkeloladata.data.remote.response.StatusResponse
import com.example.mysubmissionkeloladata.utils.AppExecutors
import com.example.mysubmissionkeloladata.vo.Resource

abstract class NetworkBoundResource<ResultType, RequestType>() {
    private var result = MediatorLiveData<Resource<ResultType>>()

    private var executors: AppExecutors? = null

    constructor(appExecutors: AppExecutors) : this() {
        this.executors = appExecutors
        result.value = Resource.loading(null)

        val dbSource = this.loadDatabase()

        result.addSource(dbSource) {
            result.removeSource(dbSource)
            if (this.shouldFetch(it)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) {
                    result.setValue(Resource.success(it))
                }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()

        result.addSource(dbSource) {
            result.setValue(Resource.loading(it))
        }

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            when (response.status) {
                StatusResponse.SUCCESS -> executors?.diskIO()?.execute {
                    saveCallResult(response.body)

                    executors?.mainThread()?.execute {
                        result.addSource(loadDatabase()) {
                            result.setValue(Resource.success(it))
                        }
                    }
                }
                StatusResponse.EMPTY -> executors?.mainThread()?.execute {
                    result.addSource(loadDatabase()) {
                        result.setValue(Resource.success(it))
                    }
                }

                StatusResponse.ERROR -> {
                    onFetchFailed()
                    result.addSource(dbSource) {
                        result.setValue(Resource.error(response.message, it))
                    }
                }
            }
        }

    }

    private fun onFetchFailed(){}

    protected abstract fun saveCallResult(data: RequestType)

    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    protected abstract fun shouldFetch(data: ResultType): Boolean

    protected abstract fun loadDatabase(): LiveData<ResultType>

    fun asLiveData(): LiveData<Resource<ResultType>> {
        return result
    }
}