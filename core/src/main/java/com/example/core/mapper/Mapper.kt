package com.example.core.mapper

interface Mapper<A, B> {
    fun map(objectToMap: A): B
}