package com.example.composemvvm.core.network

data class PaginationSource<T : Any>(
    val list: List<T>,
    val pageSize: Int,
    val pageLimit: Int
) {

    val isLastPage: Boolean get() = pageSize < pageLimit
}