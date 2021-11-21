package fx

import model.RateRecord
import java.io.File
import java.math.BigDecimal
import java.time.LocalDate

object ForeignExchangeImporter {

    fun import(): List<RateRecord> {
        val directory = File("${System.getProperty("user.dir")}/inputs/macrotrends")
        val file = File("${directory.path}/dollar-yen-exchange-rate-historical-chart.csv")

        val lines = file.readLines()

        val headers = lines.first().split(",")
        val dateIndex = headers.indexOf("date")
        val valueIndex = headers.indexOf("value")

        val rows = lines.subList(1, lines.size)
        val rawRecords = rows
            .map { line ->
                val columns = line.split(",")
                RateRecord(
                    date = LocalDate.parse(columns[dateIndex]),
                    price = BigDecimal(columns[valueIndex])
                )
            }
            .filter { it.date.year >= 2008 }

        var from = LocalDate.of(2009, 1, 1)
        val to = LocalDate.now()
        val revisedRecords = mutableListOf<RateRecord>()
        while (from < to) {
            var record = rawRecords.find { it.date == from }
            if (record != null) {
                revisedRecords.add(record)
            } else {
                var date = from.minusDays(1)
                while (true) {
                    record = rawRecords.find { it.date == date }
                    if (record != null) {
                        revisedRecords.add(record.copy(date = from))
                        break
                    } else {
                        date = date.minusDays(1)
                    }
                }
            }
            from = from.plusDays(1)
        }

        return revisedRecords
    }

}