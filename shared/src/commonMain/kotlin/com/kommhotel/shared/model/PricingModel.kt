package com.kommhotel.shared.model

/**
 * Define el modelo de precios para una habitación, permitiendo estrategias
 * de costo por noche o por bloques de horas.
 */
sealed class PricingModel {
    /**
     * Modelo de precios para hoteles tradicionales (turísticos).
     * @property pricePerNight Costo fijo por cada noche de estancia.
     */
    data class PerNight(val pricePerNight: Double) : PricingModel()

    /**
     * Modelo de precios para hoteles de paso o estancias cortas.
     * @property basePrice Costo inicial por el primer bloque de horas.
     * @property includedHours Número de horas incluidas en el costo base.
     * @property extraHourPrice Costo por cada hora adicional después del bloque inicial.
     */
    data class PerHour(
        val basePrice: Double,
        val includedHours: Int,
        val extraHourPrice: Double
    ) : PricingModel()
}