package com.insegus.securehotel_front.utils

import android.content.Context
import com.insegus.securehotel_front.R
import java.io.InputStream
import java.security.KeyStore
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

fun generateSSLContext(context: Context): SSLContext{
    val keyStore = KeyStore.getInstance("BKS")
    val i: InputStream = context.resources.openRawResource(R.raw.keystore)
    keyStore.load(i, "123456".toCharArray())

    val keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
    keyManagerFactory.init(keyStore, "123456".toCharArray())

    val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
    trustManagerFactory.init(keyStore)
    //TODO Añade clave pública del servidor
    //TODO Cargar clave privada del cliente
    val sslContext = SSLContext.getInstance("TLSv1.3")
    sslContext.init(keyManagerFactory.keyManagers, trustManagerFactory.trustManagers, null)
    return sslContext
}