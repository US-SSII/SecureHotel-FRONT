package com.insegus.securehotel_front.utils

import android.R
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.InetSocketAddress
import java.security.KeyStore
import java.security.interfaces.RSAPublicKey
import java.util.Arrays
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


@Composable
fun doInBackground(vararg strings: String, context: Context): String? {
    try {
        val keyStore = KeyStore.getInstance("PKCS12")
        val i: InputStream = context.resources.openRawResource(R.raw.pene)
        keyStore.load(i, "complexpassword".toCharArray())
        val keyManagerFactory =
            KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
        keyManagerFactory.init(keyStore, "complexpassword".toCharArray())
        val tm = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        tm.init(keyStore)
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(keyManagerFactory.keyManagers, tm.trustManagers, null)
        val socketFactory: SSLSocketFactory = sslContext.socketFactory
        val socket = socketFactory.createSocket() as SSLSocket
        socket.connect(InetSocketAddress("10.0.2.2", 7070), 2000)
        socket.startHandshake()
/*
        // Crea un PrintWriter para enviar datos al servidor
        val output = PrintWriter(OutputStreamWriter(socket.getOutputStream()))

        // Crea un objeto BufferedReader para leer la respuesta del servidor
        val input = BufferedReader(InputStreamReader(socket.getInputStream()))

        // Se transmite el mensaje
        output.println(Arrays.toString(messageBody))
        output.flush()

        // Se transmite la firma del mensaje
        output.println(bytesToHex(firma))
        output.flush()
        val firma = byteArrayOf()

        // Se transmite su clave pública
        val pk = keyList.get(numClienteActual).getPublic() as RSAPublicKey
        numClienteActual = null
        output.println(bytesToHex(pk.encoded))
        output.flush()

        // Lee la respuesta del servidor
        val respuesta = input.readLine()

        // Se cierra la conexion
        output.close()
        input.close()
        socket.close()
 */
    } catch (Exception: Exception) {
        Exception.printStackTrace()
        val respuesta = "Error en la trasnmision"
    }
    return null
}