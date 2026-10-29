package id.ac.stmi.pabkel2

import android.content.Context
import id.ac.stmi.pabkel2.manager.CartItem
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class OrderRecord(
    val id: String,
    val date: String,
    val items: String,
    val subtotal: Int,
    val tax: Int,
    val shipping: Int,
    val total: Int,
    val address: String,
    val paymentMethod: String,
    val status: String,
    val cancelReason: String,
    val rating: Float,
    val review: String
)

object OrderHistory {
    private const val PREFS = "geara_orders"
    private const val KEY = "orders"

    fun add(context: Context, items: List<CartItem>, subtotal: Int, tax: Int, shipping: Int, total: Int, address: String, method: String) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val orders = readJson(prefs.getString(KEY, "[]"))
        val lines = items.joinToString("\n") { "${it.product.name} x ${it.quantity} = ${money(it.subtotal.toInt())}" }
        orders.put(JSONObject()
            .put("id", "GR-${System.currentTimeMillis().toString().takeLast(8)}")
            .put("date", SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("id", "ID")).format(Date()))
            .put("items", lines)
            .put("subtotal", subtotal)
            .put("tax", tax)
            .put("shipping", shipping)
            .put("total", total)
            .put("address", address)
            .put("method", method)
            .put("status", "Belum Dibayar")
            .put("cancelReason", "")
            .put("rating", 0)
            .put("review", ""))
        prefs.edit().putString(KEY, orders.toString()).apply()
    }

    fun getAll(context: Context): List<OrderRecord> {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val array = readJson(prefs.getString(KEY, "[]"))
        return (array.length() - 1 downTo 0).mapNotNull { index ->
            runCatching { toRecord(array.getJSONObject(index)) }.getOrNull()
        }
    }

    fun get(context: Context, id: String): OrderRecord? = getAll(context).find { it.id == id }

    fun updateStatus(context: Context, id: String, status: String, reason: String = ""): Boolean = update(context, id) {
        it.put("status", status).put("cancelReason", reason)
    }

    fun saveReview(context: Context, id: String, rating: Float, review: String): Boolean = update(context, id) {
        it.put("rating", rating.toDouble()).put("review", review.trim())
    }

    private fun update(context: Context, id: String, change: (JSONObject) -> Unit): Boolean {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val array = readJson(prefs.getString(KEY, "[]"))
        var found = false
        for (index in 0 until array.length()) {
            val obj = array.optJSONObject(index) ?: continue
            if (obj.optString("id") == id) {
                change(obj)
                array.put(index, obj)
                found = true
                break
            }
        }
        if (found) prefs.edit().putString(KEY, array.toString()).apply()
        return found
    }

    private fun toRecord(obj: JSONObject) = OrderRecord(
        obj.optString("id"), obj.optString("date"), obj.optString("items"),
        obj.optInt("subtotal"), obj.optInt("tax"), obj.optInt("shipping"), obj.optInt("total"),
        obj.optString("address"), obj.optString("method"), obj.optString("status"),
        obj.optString("cancelReason"), obj.optDouble("rating", 0.0).toFloat(), obj.optString("review")
    )

    private fun readJson(value: String?): JSONArray = runCatching { JSONArray(value ?: "[]") }.getOrElse { JSONArray() }
    private fun money(value: Int) = "Rp" + String.format(Locale("id", "ID"), "%,d", value).replace(',', '.')
}
