package com.edwinnurwansyah.movieretrofitrxkotlin.model

import com.google.gson.JsonElement
import com.google.gson.Gson



data class BaseResponse(val status: Status, val data: JsonElement?, val error: Throwable?){


    companion object {
        fun loading(): BaseResponse {
            return BaseResponse(Status.LOADING, null, null)
        }

        fun success(data: JsonElement): BaseResponse {

            return BaseResponse(Status.SUCCESS, data, null)
        }
        fun success(): BaseResponse {

            return BaseResponse(Status.SUCCESS, null, null)
        }

        fun error(error: Throwable): BaseResponse{
            return BaseResponse(Status.ERROR, null, error)
        }
    }

}