package com.secretaria.eletronica.util

import android.content.Context
import android.content.SharedPreferences
import java.util.Calendar

class ScheduleManager(private val context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("schedule_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val PREF_LUNCH_START = "lunch_start"
        private const val PREF_LUNCH_END = "lunch_end"
        private const val PREF_WORK_START = "work_start"
        private const val PREF_WORK_END = "work_end"
    }

    fun setLunchTime(startHour: Int, startMinute: Int, endHour: Int, endMinute: Int) {
        prefs.edit().apply {
            putInt(PREF_LUNCH_START, startHour * 60 + startMinute)
            putInt(PREF_LUNCH_END, endHour * 60 + endMinute)
            apply()
        }
        Logger.i("ScheduleManager: Lunch time set - $startHour:$startMinute to $endHour:$endMinute")
    }

    fun setWorkHours(startHour: Int, startMinute: Int, endHour: Int, endMinute: Int) {
        prefs.edit().apply {
            putInt(PREF_WORK_START, startHour * 60 + startMinute)
            putInt(PREF_WORK_END, endHour * 60 + endMinute)
            apply()
        }
        Logger.i("ScheduleManager: Work hours set - $startHour:$startMinute to $endHour:$endMinute")
    }

    fun isLunchTime(): Boolean {
        val now = Calendar.getInstance()
        val currentMinutes = now.get(Calendar.HOUR_OF_DAY) * 60 + now.get(Calendar.MINUTE)
        val lunchStart = prefs.getInt(PREF_LUNCH_START, 12 * 60)
        val lunchEnd = prefs.getInt(PREF_LUNCH_END, 13 * 60)

        return currentMinutes in lunchStart until lunchEnd
    }

    fun isWorkHours(): Boolean {
        val now = Calendar.getInstance()
        val currentMinutes = now.get(Calendar.HOUR_OF_DAY) * 60 + now.get(Calendar.MINUTE)
        val workStart = prefs.getInt(PREF_WORK_START, 9 * 60)
        val workEnd = prefs.getInt(PREF_WORK_END, 18 * 60)

        return currentMinutes in workStart until workEnd
    }

    fun isWeekend(): Boolean {
        val now = Calendar.getInstance()
        val dayOfWeek = now.get(Calendar.DAY_OF_WEEK)
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY
    }

    fun isHoliday(): Boolean {
        // Implementar verificação de feriados
        // Por enquanto, retorna false
        return false
    }

    fun isVacation(): Boolean {
        // Implementar verificação de férias
        // Por enquanto, retorna false
        return false
    }

    fun getCurrentPeriod(): String {
        return when {
            isVacation() -> "vacation"
            isHoliday() -> "holiday"
            isWeekend() -> "weekend"
            isLunchTime() -> "lunch"
            !isWorkHours() -> "after_hours"
            else -> "work_hours"
        }
    }
}
