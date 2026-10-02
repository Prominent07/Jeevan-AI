package com.example.data.model

enum class AppLanguage(
    val code: String,
    val displayName: String,
    val localeTag: String
) {
    MARATHI("mr", "मराठी", "mr-IN"),
    HINDI("hi", "हिंदी", "hi-IN"),
    ENGLISH("en", "English", "en-IN")
}
