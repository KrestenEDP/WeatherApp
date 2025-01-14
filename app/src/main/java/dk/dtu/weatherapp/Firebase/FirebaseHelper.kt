package dk.dtu.weatherapp.Firebase

import com.google.firebase.firestore.FirebaseFirestore

object FirebaseHelper {
    fun isFavoriteInFirestore(
        userId: String,
        cityName: String,
        onSuccess: (Boolean) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val favoritesCollection = FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .collection("favorites")

        favoritesCollection.document(cityName).get()
            .addOnSuccessListener { document ->
                onSuccess(document.exists())
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun saveFavoriteToFirestore(
        userId: String,
        cityName: String,
        latitude: String,
        longitude: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val favoritesCollection = FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .collection("favorites")

        // Save cityName, latitude, and longitude
        val favoriteData = mapOf(
            "name" to cityName,
            "latitude" to latitude,
            "longitude" to longitude
        )

        favoritesCollection.document(cityName).set(favoriteData)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun removeFavoriteFromFirestore(
        userId: String,
        cityName: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val favoritesCollection = FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .collection("favorites")

        favoritesCollection.document(cityName).delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }
}