import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature

object DigitalSignatureHelper {
    private const val ALGORITHM = "RSA"
    private const val SIGNATURE_ALGORITHM = "SHA256withRSA"

    fun generateKeyPair(): KeyPair {
        val keyGen = KeyPairGenerator.getInstance(ALGORITHM)
        keyGen.initialize(2048) // Tamaño de la clave, puedes ajustarlo según tus necesidades
        val keyPair = keyGen.generateKeyPair()
        return keyPair
    }

    fun signData(data: ByteArray, privateKey: PrivateKey): ByteArray {
        val signature = Signature.getInstance(SIGNATURE_ALGORITHM)
        signature.initSign(privateKey)
        signature.update(data)
        return signature.sign()
    }

    fun verifySignature(signature: ByteArray, data: ByteArray, publicKey: PublicKey): Boolean {
        val signatureInstance = Signature.getInstance(SIGNATURE_ALGORITHM)
        signatureInstance.initVerify(publicKey)
        signatureInstance.update(data)
        return signatureInstance.verify(signature)
    }
}

fun main() {
    // Generar un par de claves
    val keyPair = DigitalSignatureHelper.generateKeyPair()

    // Datos originales
    val originalData = "Hello, World!".toByteArray()

    // Firmar los datos
    val signature = DigitalSignatureHelper.signData(originalData, keyPair.private)

    // Verificar la firma
    val isSignatureValid = DigitalSignatureHelper.verifySignature(signature, originalData, keyPair.public)

    if (isSignatureValid) {
        println("La firma digital es válida.")
    } else {
        println("La firma digital no es válida.")
    }
}