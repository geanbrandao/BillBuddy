package com.geanbrandao.br.billbuddy.data.local.converters

import androidx.room.TypeConverter
import com.geanbrandao.br.billbuddy.domain.model.BillStatus
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromBillStatus(value: String): BillStatus {
        return value.let { BillStatus.from(it) }
    }

    @TypeConverter
    fun stringToBillStatus(billStatus: BillStatus): String {
        return billStatus.name
    }
}
