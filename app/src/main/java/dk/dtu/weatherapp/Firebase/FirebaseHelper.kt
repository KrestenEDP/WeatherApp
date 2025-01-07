package dk.dtu.weatherapp.Firebase

import com.google.firebase.firestore.FirebaseFirestore

object FirebaseHelper {
    private val firestore by lazy { FirebaseFirestore.getInstance() }

    fun saveFavoriteToFirestore(userId: String, cityName: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val documentRef = firestore.collection("users").document(userId).collection("favorites").document(cityName)

        val favoriteCity = mapOf(
            "userId" to userId,
            "cityName" to cityName,
        )
        documentRef.set(favoriteCity)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun removeFavoriteFromFirestore(userId: String, cityName: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val documentRef = firestore.collection("users").document(userId).collection("favorites").document(cityName)

        documentRef.delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}