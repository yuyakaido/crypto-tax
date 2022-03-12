package bybit

import model.Asset
import model.DistributionRecord
import java.io.File
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object BybitImporter {

    private val directory = File("${System.getProperty("user.dir")}/inputs/bybit")

    fun importDefiMiningRecords(): List<DistributionRecord> {
        val file = File("${directory.path}/bybit_defi_mining_history.csv")
        val lines = file.readLines()
        val header = lines.first()
        val headers = header.split(",")
        val rows = lines.subList(1, lines.size)

        val effectiveUntilIndex = headers.indexOf("Effective Until (UTC)")
        val yieldIndex = headers.indexOf("Yield")

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val regex = Regex("\".+\"")

        return rows.map { row ->
            val sanitizedRow = row.replace(regex) { it.value.replace(",", "") }
            val columns = sanitizedRow.split(",")
            val effectiveUntilValue = columns[effectiveUntilIndex]
            val yieldValue = columns[yieldIndex]
            val distributedAt = LocalDateTime.parse(effectiveUntilValue, formatter).atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.systemDefault())
            val yieldAmount = BigDecimal(yieldValue.split(" ").first())
            val yieldAsset = Asset.single(yieldValue.split(" ").last())
            DistributionRecord(
                distributedAt = distributedAt,
                asset = yieldAsset,
                amount = yieldAmount
            )
        }
    }

}