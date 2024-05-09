package com.insegus.securehotel_front.utils

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.SocketException
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory


class Client(private val host: String, private val port: Int){
    private var clientSocket: SSLSocket? = null

    fun connect(context: Context){
        val sslContext = generateSSLContext(context)
        val socketFactory: SSLSocketFactory = sslContext.socketFactory

        clientSocket = socketFactory.createSocket(host, port) as SSLSocket
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
    fun receiveMessage() {
        try {
            BufferedReader(InputStreamReader(clientSocket!!.getInputStream())).use { reader ->
                Log.d("RECEIVING", "Waiting for response...")

                val receivedMessage = reader.readLine()
                Log.d("RESPONSE", "Response Received: $receivedMessage")
            }
        } catch (e: SocketException) {
            Log.e("SOCKET ERROR", "Socket exception: ${e.message}")
        } catch (e: IOException) {
            Log.e("IO ERROR", "I/O exception: ${e.message}")
        } catch (e: Exception) {
            Log.e("ERROR RECEIVING", "Error: ${e.message}")
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

