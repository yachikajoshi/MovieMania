package com.yachikajoshi.movielist.common

/**
 * With the help of this class
 * we can represent the data easily
 * in our views
 * **/
sealed class Resource<T>(val data: T, val message: String? = null) {

    class Success<T>(data: T) : Resource<T>(data)

    class Error<T>(message: String, data: T) : Resource<T>(data, message)

    class Loading<T>(data: T) : Resource<T>(data)
}
