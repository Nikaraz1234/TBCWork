package com.example.tbcworks.presentation.screen.home.model

data class Section(
    val title: String,
    val items: List<Any>,
    val type: SectionType
){
    enum class SectionType {
        UPCOMING, CATEGORY, TRENDING, FAQ
    }
}