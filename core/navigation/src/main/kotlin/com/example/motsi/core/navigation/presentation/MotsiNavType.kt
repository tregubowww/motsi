package com.example.motsi.core.navigation.presentation

import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

// костыль для сериализации может быть в более новых версиях compose navigation  поправится и добавится возможность использовать разные типы
class MotsiNavType<T>(private val serializer: KSerializer<T>) :
    NavType<T>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): T? {
        val value = bundle.getString(key) ?: return null
        return Json.decodeFromString(serializer, value)
    }

    override fun parseValue(value: String): T =
        Json.decodeFromString(serializer, value)

    // It's important to override this method, since the
    // default implementation just calls `toString` on the
    // value, which will not work.
    override fun serializeAsValue(value: T): String {
        return Json.encodeToString(serializer, value)
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, Json.encodeToString(serializer, value))
    }
}