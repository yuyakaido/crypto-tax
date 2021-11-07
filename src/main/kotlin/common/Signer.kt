package common

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object Signer {

    enum class Algorithm(val value: String) {
        HMAC_SHA_256("HmacSHA256"),
        HMAC_SHA_512("HmacSHA512")
    }

    fun generateSignature(
        apiKey: String,
        apiSecret: String,
        target: String,
        algorithm: Algorithm = Algorithm.HMAC_SHA_256
    ): String {
        val mac = Mac.getInstance(algorithm.value)
        mac.init(SecretKeySpec(apiSecret.toByteArray(), algorithm.value))
        return mac.doFinal(target.toByteArray()).joinToString("") { String.format("%02x", it) }
    }
}