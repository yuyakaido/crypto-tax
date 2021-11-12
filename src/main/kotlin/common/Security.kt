package common

import java.security.MessageDigest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object Security {

    enum class HashAlgorithm(val value: String) {
        SHA_512("SHA-512")
    }

    enum class SignatureAlgorithm(val value: String) {
        HMAC_SHA_256("HmacSHA256"),
        HMAC_SHA_512("HmacSHA512")
    }

    private fun ByteArray.toHexString(): String {
        return joinToString("") { String.format("%02x", it) }
    }

    fun generateHash(
        target: String,
        algorithm: HashAlgorithm = HashAlgorithm.SHA_512
    ): String {
        return MessageDigest
            .getInstance(algorithm.value)
            .digest(target.toByteArray())
            .toHexString()
    }

    fun generateSignature(
        apiSecret: String,
        target: String,
        algorithm: SignatureAlgorithm = SignatureAlgorithm.HMAC_SHA_256
    ): String {
        val mac = Mac.getInstance(algorithm.value)
        mac.init(SecretKeySpec(apiSecret.toByteArray(), algorithm.value))
        return mac.doFinal(target.toByteArray()).toHexString()
    }
}