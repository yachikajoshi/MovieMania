package com.yachikajoshi.movielist.data.repo

import com.yachikajoshi.movielist.data.model.Movies
import com.yachikajoshi.movielist.domain.repo.MoviesRepository

class FakeMovieRepo : MoviesRepository {

    private val movieList = listOf(
        Movies.MovieDetail(
            "",
            "Default",
            "Bla Bla",
            "1997",
            "Dom",
            "https://picsum.photos/200/300/?blur",
            "",
            "",
            "",
            "",
            ""
        ),
        Movies.MovieDetail(
            "",
            "Default",
            "Bla Bla",
            "1997",
            "Dom",
            "https://picsum.photos/id/237/200/300",
            "",
            "",
            "",
            "",
            ""
        ),
        Movies.MovieDetail(
            "",
            "Default",
            "Bla Bla",
            "1997",
            "Dom",
            "https://picsum.photos/200/300?grayscale",
            "",
            "",
            "",
            "",
            ""
        ),
        Movies.MovieDetail(
            "",
            "Default",
            "Bla Bla",
            "1997",
            "Dom",
            "https://picsum.photos/seed/picsum/200/300",
            "",
            "",
            "",
            "",
            ""
        ),
        Movies.MovieDetail(
            "",
            "Default",
            "Bla Bla",
            "1997",
            "Dom",
            "https://picsum.photos/id/870/200/300?grayscale&blur=2",
            "",
            "",
            "",
            "",
            ""
        )
    )

    override suspend fun getTop10Movies(): Movies {
        return Movies(movieList, "")
    }

    override suspend fun getComingSoonMovies(): Movies {
        return Movies(movieList, "")
    }

    override suspend fun getTopTVShows(): Movies {
        return Movies(movieList, "")
    }
}
