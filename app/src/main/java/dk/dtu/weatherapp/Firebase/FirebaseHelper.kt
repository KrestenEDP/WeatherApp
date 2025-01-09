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
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val favoritesCollection = FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .collection("favorites")

        favoritesCollection.document(cityName).set(mapOf("name" to cityName))
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
