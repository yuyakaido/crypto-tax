package model

import java.time.ZonedDateTime

sealed class RecordType {
    abstract fun recordedAt(): ZonedDateTime
    abstract fun asset(): Asset
}
