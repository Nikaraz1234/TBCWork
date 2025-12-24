package com.example.tbcworks.presentation.screen.event_detail.mapper

import com.example.tbcworks.domain.model.event.Address

fun Address.toUiString(): String =
    "$street, $city"