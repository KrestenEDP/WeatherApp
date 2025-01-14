package dk.dtu.weatherapp.Firebase

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirebaseHelper {
    fun isLocationInFirestore(
        userId: String,
        tableId: String,
        cityName: String,
        onSuccess: (Boolean) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val collection = FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .collection(tableId)

        collection.document(cityName).get()
            .addOnSuccessListener { document ->
                onSuccess(document.exists())
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    suspend fun limitEntriesInFirestoreCollection(
        userId: String,
        tableId: String,
        amount: Int
    ) {
        val collection = FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .collection(tableId)

        while (collection.get().await().size() > amount) {
            val oldestDocument = collection.get().await().documents.minByOrNull { document ->
                document.getTimestamp("timestamp")?.toDate()?.time ?: Long.MAX_VALUE
            }

            if (oldestDocument != null) {
                collection.document(oldestDocument.id).delete()
                    .addOnFailureListener { exception ->
                        println("Error oldest entry: ${exception.message}") }
            }
        }
    }

    fun saveLocationToFirestore(
        userId: String,
        tableId: String,
        cityName: String,
        latitude: String,
        longitude: String,
        saveTimeStamp: Boolean = false,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val collection = FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .collection(tableId)

        // Save cityName, latitude, and longitude
        val data = if (saveTimeStamp) mapOf(
            "name" to cityName,
            "latitude" to latitude,
            "longitude" to longitude,
            "timestamp" to FieldValue.serverTimestamp()
        ) else mapOf(
            "name" to cityName,
            "latitude" to latitude,
            "longitude" to longitude
        )

        collection.document(cityName).set(data)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun removeLocationFromFirestore(
        userId: String,
        tableId: String,
        cityName: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val collection = FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .collection(tableId)

        collection.document(cityName).delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }
}