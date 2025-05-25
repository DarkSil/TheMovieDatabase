package com.gliskstudio.themoviedatabaseta.utils

import com.gliskstudio.themoviedatabaseta.model.MovieItem

object Utils {

    fun mockMovieItem() : MovieItem {
        return MovieItem(
            0,
            "Title #1",
            "Description duis aute irure dolor in reprehenderit in voluptate velit...",
            "https://media.themoviedb.org/t/p/w220_and_h330_face/crDALXiyHVZg2xIIErMaBmTC3e3.jpg",
            "9.0/10",
            "01.01.1999"
        )
    }

    fun mockMovieList() : ArrayList<MovieItem> {
        return arrayListOf(
            mockMovieItem(),
            mockMovieItem(),
            mockMovieItem()
        )
    }

}