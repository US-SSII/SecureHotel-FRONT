package com.insegus.securehotel_front.utils

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.insegus.securehotel_front.R
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

fun generateSSLContext(context: Context): SSLContext {
    val inputStream = context.resources.openRawResource(R.raw.certificate)
    val certificateFactory = CertificateFactory.getInstance("X.509")
    val certificate = certificateFactory.generateCertificate(inputStream)

    val keyStore = KeyStore.getInstance("AndroidKeyStore")
    keyStore.load(null) // Load the keystore from the Android system's keychain
    keyStore.setCertificateEntry("server", certificate)

    // Generate a new RSA key pair entry in the Android KeyStore
    val alias = "myKeyAlias"
    if (!keyStore.containsAlias(alias)) {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore")
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(alias, KeyProperties.PURPOSE_SIGN)
            .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
            .setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)
            .setKeySize(2048)
            .build()
        keyPairGenerator.initialize(keyGenParameterSpec)
        keyPairGenerator.generateKeyPair()
    }

    // Initialize the KeyManagerFactory and TrustManagerFactory instances
    val keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
    keyManagerFactory.init(keyStore, null)

    val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
    trustManagerFactory.init(keyStore)


    // Initialize the SSLContext instance
    val sslContext = SSLContext.getInstance("TLSv1.3")
    sslContext.init(keyManagerFactory.keyManagers,  arrayOf(createCustomTrustManager()), null)
    return sslContext
}
private fun createCustomTrustManager(): X509TrustManager {
    return object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}

        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}

        override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()
    }
}