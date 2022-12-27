package com.example.domain.mapper

import com.example.data.model.dto.ArticleDto
import com.example.domain.model.PublishedAt
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import okhttp3.internal.UTC
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.text.SimpleDateFormat
import java.util.*

class ArticleMapperTest {

    private val mapper = ArticleMapper()

    private val articleDto: ArticleDto = mockk(relaxed = true)
    private val author = "author"
    private val title = "title"
    private val description = "description"
    private val urlToImage = "urlToImage"
    private val publishedAt = "publishedAt"

    private val date: Date = mockk(relaxed = true)
    private val time = 0L

    @BeforeEach
    fun setUp() {
        mockkConstructor(SimpleDateFormat::class)
        every { anyConstructed<SimpleDateFormat>().timeZone } returns UTC
        every { anyConstructed<SimpleDateFormat>().parse(any()) } returns date
        every { date.time } returns time
        every { articleDto.publishedAt } returns publishedAt
    }

    @Test
    fun `map author`() {
        every { articleDto.author } returns author
        val result = mapper.map(articleDto)

        assertEquals(author, result.author)
    }

    @Test
    fun `map null author`() {
        every { articleDto.author } returns null
        val result = mapper.map(articleDto)

        assertEquals("", result.author)
    }

    @Test
    fun `map title`() {
        every { articleDto.title } returns title
        val result = mapper.map(articleDto)

        assertEquals(title, result.title)
    }

    @Test
    fun `map null title`() {
        every { articleDto.title } returns null
        val result = mapper.map(articleDto)

        assertEquals("", result.title)
    }

    @Test
    fun `map description`() {
        every { articleDto.description } returns description
        val result = mapper.map(articleDto)

        assertEquals(description, result.description)
    }

    @Test
    fun `map null description`() {
        every { articleDto.description } returns null
        val result = mapper.map(articleDto)

        assertEquals("", result.description)
    }

    @Test
    fun `map urlToImage`() {
        every { articleDto.urlToImage } returns urlToImage
        val result = mapper.map(articleDto)

        assertEquals(urlToImage, result.urlToImage)
    }

    @Test
    fun `map null urlToImage`() {
        every { articleDto.urlToImage } returns null
        val result = mapper.map(articleDto)

        assertEquals("", result.urlToImage)
    }

    @Test
    fun `map publishedAt`() {
        every { articleDto.publishedAt } returns publishedAt
        val result = mapper.map(articleDto)

        assertEquals(PublishedAt(publishedAt), result.publishedAt)
    }

    @Test
    fun `map null publishedAt`() {
        every { articleDto.publishedAt } returns null
        val result = mapper.map(articleDto)

        assertEquals(PublishedAt(null), result.publishedAt)
    }
}
