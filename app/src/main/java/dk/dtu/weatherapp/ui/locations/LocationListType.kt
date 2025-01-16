package dk.dtu.weatherapp.ui.locations

import dk.dtu.weatherapp.R

interface LocationListType {
    val icon: Int
    val title: String
    val noElementsText: String
}

object SearchLocations : LocationListType {
    override val icon: Int = R.drawable.location
    override val title: String = "Search"
    override val noElementsText: String = "No locations found with the current search keyword"
}

object RecentLocations : LocationListType {
    override val icon: Int = R.drawable.recent
    override val title: String = "Recent"
    override val noElementsText: String = "No recent locations"
}

object FavoriteLocations : LocationListType {
    override val icon: Int = R.drawable.favorite
    override val title: String = "Favorites"
    override val noElementsText: String = "No favorite locations"
}