package com.loskon.usercrud.data.converter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeConverter : JsonDeserializer<LocalDateTime>, JsonSerializer<LocalDateTime> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDateTime? {
        return json?.let {
            ZonedDateTime.parse(json.asJsonPrimitive.asString, DateTimeFormatter.ISO_DATE_TIME)
                .withZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime()
        }
    }

    override fun serialize(
        src: LocalDateTime?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement? {
        return src?.let {
            JsonPrimitive(ZonedDateTime.of(it, ZoneId.systemDefault()).toLocalDateTime().toString())
        }
    }
}