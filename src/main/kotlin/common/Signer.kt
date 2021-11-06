package common

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

interface Signer {
    fun generateSignature(
        apiKey: String,
        apiSecret: String,
        target: String
    ): String {
        val algorithm = "HmacSHA256"
        val mac = Mac.getInstance(algorithm)
        mac.init(SecretKeySpec(apiSecret.toByteArray(), algorithm))
        return mac.doFinal(target.toByteArray()).joinToString("") { String.format("%02x", it) }
    }
}