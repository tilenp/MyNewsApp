package com.example.core.model

class ArticleRequest private constructor(
    val query: String,
    val pageSize: Int,
) {
    class Builder(
        private var query: String
    ) {
        private var pageSize: Int = DEFAULT_PAGE_SIZE

        fun query(query: String): Builder {
            if (query.length <= QUERY_LENGTH_LIMIT) {
                this.query = query
            }
            return this
        }

        fun pageSize(pageSize: Int): Builder {
            if (pageSize <= PAGE_SIZE_LIMIT) {
                this.pageSize = pageSize
            }
            return this
        }

        fun build(): ArticleRequest {
            return ArticleRequest(
                query = query,
                pageSize = pageSize,
            )
        }
    }

    companion object {
        private const val QUERY_LENGTH_LIMIT = 500
        private const val PAGE_SIZE_LIMIT = 100
        private const val DEFAULT_PAGE_SIZE = 10
    }
}
