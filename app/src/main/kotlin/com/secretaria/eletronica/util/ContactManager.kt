package com.secretaria.eletronica.util

import android.content.Context
import android.provider.ContactsContract
import android.telephony.TelephonyManager

class ContactManager(private val context: Context) {

    fun getContactNameByNumber(phoneNumber: String): String? {
        return try {
            val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val selection = "${ContactsContract.CommonDataKinds.Phone.NUMBER} = ?"
            val selectionArgs = arrayOf(phoneNumber)

            val cursor = context.contentResolver.query(
                uri,
                projection,
                selection,
                selectionArgs,
                null
            )

            var contactName: String? = null
            cursor?.use {
                if (it.moveToFirst()) {
                    contactName = it.getString(0)
                }
            }

            contactName
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getSimSlot(context: Context): Int {
        return try {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            // Retorna 1 para SIM 1, 2 para SIM 2, etc.
            // Nota: Esta é uma abordagem simplificada
            1
        } catch (e: Exception) {
            e.printStackTrace()
            1
        }
    }

    fun formatPhoneNumber(phoneNumber: String): String {
        return phoneNumber.replace(Regex("[^0-9+]"), "")
    }

    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val formatted = formatPhoneNumber(phoneNumber)
        return formatted.length >= 10
    }
}
