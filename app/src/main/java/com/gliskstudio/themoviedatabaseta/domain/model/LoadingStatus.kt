package com.gliskstudio.themoviedatabaseta.domain.model

sealed interface LoadingStatus {

    data object EmptyQuery : LoadingStatus
    data object PageOverload : LoadingStatus
    data object LimitExceeded : LoadingStatus

    data class InProgress(
        val isPageLoading: Boolean = false
    ) : LoadingStatus

    data class Loaded(
        val list: List<MovieItem>
    ) : LoadingStatus

    data class Error(
        val errorCode: Int
    ) : LoadingStatus

}