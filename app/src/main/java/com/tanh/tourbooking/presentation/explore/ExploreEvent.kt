package com.tanh.tourbooking.presentation.explore

sealed class ExploreEvent {
    data class TypePlace(val place: String): ExploreEvent()
    data class TypeDepartureDate(val date: String): ExploreEvent()
    data object StartFilter : ExploreEvent()
    data class OnPriceRangeChange(val minPrice: Double, val maxPrice: Double) : ExploreEvent()
}