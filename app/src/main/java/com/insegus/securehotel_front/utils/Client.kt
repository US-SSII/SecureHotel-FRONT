package com.insegus.securehotel_front.utils

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import org.xml.sax.InputSource
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.InetSocketAddress
import java.net.URL
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory
import javax.xml.parsers.SAXParserFactory


class Client(private val host: String, private val port: Int){
    private var clientSocket: SSLSocket? = null

    fun connect(context: Context){
        val sslContext = generateSSLContext(context)
        val socketFactory: SSLSocketFactory = sslContext.socketFactory

        clientSocket = socketFactory.createSocket(host, port) as SSLSocket
        clientSocket!!.startHandshake()
    }
    fun connect2(context: Context){
        val socketFactory = generateSSLContext(context).socketFactory
        val sslSocket = socketFactory.createSocket(host, port) as SSLSocket

        sslSocket.enabledCipherSuites = sslSocket.supportedCipherSuites

        val inputStream = sslSocket.inputStream
        val outputStream = sslSocket.outputStream
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
class ConnectTask(private val context: Context, private val client: Client) : AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg params: Void?): Void? {
        // Perform network operation here using the client object
        client.connect2(context)
        return null
    }
}

