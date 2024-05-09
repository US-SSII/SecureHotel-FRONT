package com.insegus.securehotel_front.utils

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.insegus.securehotel_front.R
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

fun generateSSLContext(context: Context): SSLContext {
    // Load the certificate from the resources
    val inputStream = context.resources.openRawResource(R.raw.certificate_server)
    val certificateFactory = CertificateFactory.getInstance("X.509")
    val certificate = certificateFactory.generateCertificate(inputStream)

    // Load the Android KeyStore
    val keyStore = KeyStore.getInstance("AndroidKeyStore")
    keyStore.load(null)

    // Check if the RSA key pair already exists in the KeyStore, if not, generate a new one
    val alias = "myKeyAlias"
    if (!keyStore.containsAlias(alias)) {
        val keyPair = generateKeyPair()
        keyStore.setKeyEntry(alias, keyPair.private, null, arrayOf(certificate))
    }

    // Initialize the KeyManagerFactory and TrustManagerFactory instances with the KeyStore
    val keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
    keyManagerFactory.init(keyStore, null)

    val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
    trustManagerFactory.init(keyStore)

    // Initialize the SSLContext instance
    val sslContext = SSLContext.getInstance("TLSv1.3")
    sslContext.init(keyManagerFactory.keyManagers, arrayOf(createCustomTrustManager()), null)
    return sslContext
}

private fun generateKeyPair(): KeyPair {
    val keyPairGenerator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore")
    val keyGenParameterSpec = KeyGenParameterSpec.Builder(
        "myKeyAlias",
        KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
    )
        .setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)
        .setDigests(KeyProperties.DIGEST_SHA256)
        .build()

    keyPairGenerator.initialize(keyGenParameterSpec)
    return keyPairGenerator.generateKeyPair()
}

private fun createCustomTrustManager(): X509TrustManager {
    return object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}

        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}

        override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()
    }
}
