package com.insegus.securehotel_front.utils

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.InetSocketAddress
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory


class Client(private val host: String, private val port: Int){
    private var clientSocket: SSLSocket? = null

    fun connect(context: Context){
        val sslContext = generateSSLContext(context)
        val socketFactory: SSLSocketFactory = sslContext.socketFactory

        clientSocket = socketFactory.createSocket() as SSLSocket
        clientSocket!!.connect(InetSocketAddress(host, port), 2000)
        clientSocket!!.startHandshake()
    }
    fun sendMessage(message:String){
        try{
            val outputStream = clientSocket!!.getOutputStream()
            outputStream.write(message.toByteArray())
            outputStream.flush()
            Log.d("SENDING", "Message Sended: $message")
        }
        catch (e: Exception) {
            e.printStackTrace()
            Log.e("ERROR SENDING", "Error: $e")
        }
    }
    fun recieveMessage(){
        try {
            val response = BufferedReader(InputStreamReader(clientSocket!!.getInputStream()))
            val receivedMessage = response.readLine()
            Log.d("RESPONSE", "Response Received: $receivedMessage")
        }
        catch (e: Exception) {
            e.printStackTrace()
            Log.e("ERROR RECEIVING", "Error: $e")
        }
    }
    fun close(){
        try{
            clientSocket!!.close()
            Log.d("CLOSING", "Closing Connection...")
        }
        catch (e: Exception) {
            e.printStackTrace()
            Log.e("ERROR CLOSING", "Error: $e")
        }
    }
}