package com.kommhotel.shared.model

/**
 * Representa una habitación en el hotel, con un modelo de precios flexible.
 *
 * @property id Identificador único de la habitación.
 * @property name Nombre descriptivo de la habitación (ej. "Suite Presidencial").
 * @property number Número de la habitación (ej. "101", "203B").
 * @property type Tipo de habitación (ej. "Sencilla", "Doble", "Suite").
 * @property description Breve descripción de la habitación y sus amenidades.
 * @property capacity Número máximo de huéspedes que puede alojar.
 * @property pricing El modelo de precios que aplica a esta habitación (por noche o por hora).
 * @property isAvailable Estado de disponibilidad actual.
 */
data class Habitacion(
    val id: String,
    val name: String,
    val number: String,
    val type: String,
    val description: String,
    val capacity: Int,
    val pricing: PricingModel,
    val isAvailable: Boolean
)