package com.insegus.securehotel_front.utils

import android.content.Context
import com.insegus.securehotel_front.R
import java.io.InputStream
import java.security.KeyStore
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

fun generateSSLContext(context: Context, keystoreName: String, alias: String, password: CharArray): SSLContext {
    val keyStore = KeyStore.getInstance("JKS")
    val i: InputStream = context.resources.openRawResource(context.resources.getIdentifier(keystoreName, "raw", context.packageName))
    keyStore.load(i, password)

    val keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
    keyManagerFactory.init(keyStore, password)

    val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
    trustManagerFactory.init(keyStore)

    val sslContext = SSLContext.getInstance("TLSv1.3")
    sslContext.init(keyManagerFactory.keyManagers, trustManagerFactory.trustManagers, null)
    return sslContext
}