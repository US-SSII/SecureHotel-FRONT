package com.insegus.securehotel_front.utils

import android.content.Context
import com.insegus.securehotel_front.R
import java.io.InputStream
import java.security.KeyStore
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

fun generateSSLContext(context: Context): SSLContext{
    val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
    val i: InputStream = context.resources.openRawResource(R.raw.keystore)
    keyStore.load(i, "complexpassword".toCharArray())

    val keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
    keyManagerFactory.init(keyStore, "complexpassword".toCharArray())

    val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
    trustManagerFactory.init(keyStore)

    val sslContext = SSLContext.getInstance("TLSv1.3")
    sslContext.init(keyManagerFactory.keyManagers, trustManagerFactory.trustManagers, null)
    return sslContext
}