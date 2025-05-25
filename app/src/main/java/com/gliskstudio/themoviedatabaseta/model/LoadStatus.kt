package com.gliskstudio.themoviedatabaseta.model

sealed interface LoadStatus {

    data object EmptyQuery : LoadStatus
    data object InProgress : LoadStatus

    data class Loaded(
        val list: List<MovieItem>
    ) : LoadStatus

    data class Error(
        val errorCode: Int
    ) : LoadStatus

}